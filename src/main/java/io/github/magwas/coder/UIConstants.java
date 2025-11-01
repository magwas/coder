package io.github.magwas.coder;

public interface UIConstants {
	String PROMPT_MESSAGE =
			"OpenRouter AI Client with Spring Boot\nConversation history maintained across requests\nCommands: '/clear', '/exit', '/history', '/instructions'\nEnter multiline input ending with '.'";
	String GOT_INPUT = "--- got it ---";
	String GOODBYE_MESSAGE = "Goodbye!";
	String INPUT_PROMPT = ">>> ";
	String MULTILINE_END = ".";
	String ERROR_PREFIX = "Error: ";
	String CLEAR_CONFIRMATION = "Conversation history cleared.";
	String HISTORY_MESSAGE = "History messages: ";
	String INSTRUCTIONS_STATUS = "System instructions: ";
	String INSTRUCTIONS_LOADED = "LOADED";
	String INSTRUCTIONS_MISSING = "NOT FOUND";
	String SYSTEM_PROMPT =
			"From now on, your answer should be a proper xml. Use a root element <root>. Files are in <file name=\"path/name.ext\"> tags. Make sure you escape < and &.\n"
					+ "The answer should not contain anything else but xml.\n"
					+ "By default you write programs in java 21.\n";
	String CURRENT_CODE_SECTION = "\nCurrent code:\n";
}
