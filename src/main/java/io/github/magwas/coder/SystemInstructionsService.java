package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public class SystemInstructionsService {
	public String apply(PersonalityData personality) {
		try {
			Path instructionsFile = Path.of(System.getProperty("user.home"), ".coder", personality.instructionsFile());
			return Files.exists(instructionsFile)
					? Files.readString(instructionsFile).trim()
					: "";
		} catch (IOException e) {
			return "";
		}
	}
}
