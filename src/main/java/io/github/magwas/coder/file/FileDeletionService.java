package io.github.magwas.coder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import io.github.magwas.coder.ErrorMessages;

@Service
public class FileDeletionService implements ErrorMessages {
	public Void apply(String fileName) {
		try {
			Path path = Path.of(fileName);
			Files.deleteIfExists(path);
			return null;
		} catch (IOException e) {
			throw new RuntimeException(DELETION_ERROR, e);
		}
	}
}
