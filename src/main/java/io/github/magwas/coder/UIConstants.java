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
	String CURRENT_CODE_SECTION = "\nCurrent code:\n";
	String BAD_XML_PROMPT = """
					Your response as an AI was not a proper XML:
					{0}

					Try again.
					""";
	String PERSONALITY_CHANGED = "Personality changed to: %s";
	String PERSONALITY_USAGE = "Usage: /personality <name>";
}
