package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class SourceCodeReaderService implements SourceCodeReaderConstants {
	public String apply() {
		StringBuilder xml = new StringBuilder();
		try {
			processDirectory(Path.of(SRC_MAIN_JAVA), xml);
			processDirectory(Path.of(SRC_TEST_JAVA), xml);
		} catch (IOException e) {
			return "";
		}
		return xml.toString();
	}

	private void processDirectory(Path dir, StringBuilder xml) throws IOException {
		Path projectRoot = Paths.get("").toAbsolutePath();
		if (!Files.exists(dir)) {
			return;
		}

		// Ensure we're working with absolute paths
		Path absoluteDir = dir.toAbsolutePath();
		Path absoluteProjectRoot = projectRoot.toAbsolutePath();

		if (!Files.exists(absoluteDir)) {
			return;
		}

		Files.walk(absoluteDir)
				.filter(Files::isRegularFile)
				.filter(path -> path.toString().endsWith(".java"))
				.forEach(path -> {
					try {
						String content = Files.readString(path);

						// Ensure both paths are absolute before relativizing
						Path absolutePath = path.toAbsolutePath();
						String relativePath;

						try {
							relativePath = absoluteProjectRoot
									.relativize(absolutePath)
									.toString()
									.replace("\\", "/");
						} catch (IllegalArgumentException e) {
							// If paths are on different file systems, use absolute path
							relativePath = absolutePath.toString().replace("\\", "/");
						}

						xml.append("<file name=\"")
								.append(relativePath)
								.append("\">")
								.append(content)
								.append("</file>\n");
					} catch (IOException e) {
						System.err.println("Cannot read file: " + path);
					}
				});
	}
}
