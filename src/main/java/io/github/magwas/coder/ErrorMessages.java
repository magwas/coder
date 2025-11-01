package io.github.magwas.coder;

public interface ErrorMessages {
	String API_KEY_ERROR = "OpenRouter API key file not found at: %s";
	String FILE_ERROR = "Error saving AI content";
	String API_ERROR = "Error calling OpenRouter API";
	String ERROR_TEMPLATE = "HTTP error %d: %s";
	String PATH_TRAVERSAL_ERROR = "Path traversal attempt detected";
	String DELETION_ERROR = "Error deleting file";
	String CONFIG_NOT_LOADED = "Config not loaded";
	String PERSONALITY_NOT_FOUND = "Personality not found: %s";
	String CONFIG_FILE_NOT_FOUND = "Config file not found: %s";
}
