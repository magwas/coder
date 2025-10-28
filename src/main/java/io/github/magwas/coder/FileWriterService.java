package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

@Service
public class FileWriterService implements ErrorMessages {
	public Void apply(String fileName, String content) {
		try {
			Path path = Path.of(fileName);
			Files.createDirectories(path.getParent());
			Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			return null;
		} catch (IOException e) {
			throw new RuntimeException(ErrorMessages.FILE_ERROR, e);
		}
	}
}
