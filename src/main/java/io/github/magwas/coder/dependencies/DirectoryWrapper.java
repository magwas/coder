package io.github.magwas.coder.dependencies;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class DirectoryWrapper {
	public Path getCurrentDir() {
		return Paths.get("").toAbsolutePath();
	}
}
