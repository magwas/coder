package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemInstructionsService {
	@Autowired
	private PersonalityService personalityService;

	public String apply(String personalityName) {
		try {
			PersonalityData personality = personalityService.apply(personalityName);
			Path instructionsFile = Path.of(System.getProperty("user.home"), ".coder", personality.instructionsFile());
			return Files.exists(instructionsFile)
					? Files.readString(instructionsFile).trim()
					: "";
		} catch (IOException e) {
			return "";
		} catch (IllegalArgumentException e) {
			return "";
		}
	}
}
