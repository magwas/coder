package io.github.magwas.coder.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class ReadFilesCommandService implements ProcessingStepService {

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() < 2) return new ProcessingContextData(400, "usage: readFiles dirName*", context.meta());
		StringBuilder stringBuilder = new StringBuilder();
		try {
			for (String directory : args.subList(1, args.size())) processDirectory(Path.of(directory), stringBuilder);
		} catch (IOException e) {
			return new ProcessingContextData(500, e.getMessage(), context.meta());
		}
		String xml = stringBuilder.toString();
		return new ProcessingContextData(200, xml, context.meta());
	}

	private void processDirectory(Path dir, StringBuilder xml) throws IOException {
		Path projectRoot = Paths.get("").toAbsolutePath();
		if (!Files.exists(dir)) {
			return;
		}

		Path absoluteDir = dir.toAbsolutePath();

		if (!Files.exists(absoluteDir)) {
			return;
		}
		try (Stream<Path> paths = Files.walk(absoluteDir)) {

			paths.filter(Files::isRegularFile)
					.filter(path -> path.toString().endsWith(".java"))
					.forEach(readOneFile(xml, projectRoot));
		}
	}

	private static Consumer<Path> readOneFile(StringBuilder xml, Path absoluteProjectRoot) {
		return path -> {
			try {
				String content = Files.readString(path);

				String relativePath = absoluteProjectRoot
						.relativize(path.toAbsolutePath())
						.toString()
						.replace("\\", "/");

				xml.append("<file name=\"")
						.append(relativePath)
						.append("\">")
						.append(content)
						.append("</file>\n");
			} catch (IOException e) {
				System.err.println("Cannot read file: " + path);
			}
		};
	}
}
