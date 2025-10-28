package io.github.magwas.coder;

import java.nio.file.Path;

public final class PathUtil {
	private PathUtil() {}

	public static boolean isPathAllowed(Path path, Path currentDir) {
		return path.toString().startsWith(currentDir.normalize().toString());
	}
}
