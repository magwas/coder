package io.github.magwas.coder;

public interface UIConstants {
	String PROMPT_MESSAGE =
			"""
			OpenRouter AI Client with Spring Boot
			Conversation history maintained across requests
			Commands: '/clear', '/exit', '/history', '/instructions'
			Enter multiline input ending with '.'""";
	String GOODBYE_MESSAGE = "Goodbye!";
	String INPUT_PROMPT = ">>> ";
	String MULTILINE_END = ".";
	String ERROR_PREFIX = "Error: ";
	String CLEAR_CONFIRMATION = "Conversation history cleared.";
	String HISTORY_MESSAGE = "History messages: ";
	String INSTRUCTIONS_STATUS = "System instructions: ";
	String INSTRUCTIONS_LOADED = "LOADED";
	String INSTRUCTIONS_MISSING = "NOT FOUND";
}
