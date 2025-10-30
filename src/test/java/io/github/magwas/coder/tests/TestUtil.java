package io.github.magwas.coder.tests;

import java.lang.reflect.Field;

public final class TestUtil {

	private TestUtil() {
		// Utility class
	}

	@SuppressWarnings("unchecked")
	public static <T> T dependency(Object target, Class<T> dependencyClass) {
		try {
			for (Field field : target.getClass().getDeclaredFields()) {
				if (field.getType().equals(dependencyClass)) {
					field.setAccessible(true);
					return (T) field.get(target);
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to access dependency field", e);
		}
		throw new IllegalArgumentException("No field found of type " + dependencyClass);
	}
}
