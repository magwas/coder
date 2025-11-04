package io.github.magwas.coder.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SystemInstructionsCommandService implements ProcessingStepService {

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() < 2)
			return new ProcessingContextData(500, "usage: systemInstructions filename*", context.meta());
		StringBuilder sb = new StringBuilder();
		for (String file : args.subList(1, args.size())) {
			try {
				Path instructionsFile = Path.of(System.getProperty("user.home"), ".coder", file);
				if (Files.exists(instructionsFile)) {
					sb.append(Files.readString(instructionsFile)).append("\n");
				}
			} catch (IOException e) {
				return new ProcessingContextData(500, "not found: " + file, context.meta());
			}
		}
		return new ProcessingContextData(200, sb.toString().trim(), context.meta());
	}
}
