package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public class SystemInstructionsService {
	public String apply(PersonalityData personality) {
		StringBuilder sb = new StringBuilder();
		for (String file : personality.instructionsFile()) {
			try {
				Path instructionsFile = Path.of(System.getProperty("user.home"), ".coder", file);
				if (Files.exists(instructionsFile)) {
					sb.append(Files.readString(instructionsFile)).append("\n");
				}
			} catch (IOException e) {
				// Ignore individual file errors
			}
		}
		return sb.toString().trim();
	}
}
