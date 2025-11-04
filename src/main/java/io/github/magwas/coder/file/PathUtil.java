package io.github.magwas.coder.file;

import java.nio.file.Path;

public final class PathUtil {
	private PathUtil() {}

	public static boolean isPathDisAllowed(Path path, Path currentDir) {
		return !path.toString().startsWith(currentDir.normalize().toString());
	}
}
