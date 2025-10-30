package io.github.magwas.coder;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainLoopService implements UIConstants {
	@Autowired
	private OpenRouterClientService openRouterClientService;

	@Autowired
	private ConsoleInputService consoleInputService;

	@Autowired
	private ConversationClearService conversationClearService;

	@Autowired
	private ConversationSizeService conversationSizeService;

	@Autowired
	private ConversationHasSystemInstructionsService conversationHasSystemInstructionsService;

	@Autowired
	private ConversationSetupService conversationSetupService;

	@Autowired
	private LineReaderComponent lineReaderComponent;

	@Autowired
	private SystemDependency systemDependency;

	public Void apply() throws Exception {
		LineReader lineReader = lineReaderComponent.getLineReader();
		systemDependency.println(PROMPT_MESSAGE);
		conversationSetupService.apply();

		while (true) {
			String userInput = consoleInputService.apply(lineReader);
			if (userInput == null) break;
			if (userInput.isEmpty()) continue;

			switch (userInput.toLowerCase()) {
				case CommandConstants.CMD_CLEAR -> handleClear();
				case CommandConstants.CMD_HISTORY -> handleHistory();
				case CommandConstants.CMD_INSTRUCTIONS -> handleInstructions();
				default -> handleQuestion(userInput);
			}
		}
		systemDependency.println(GOODBYE_MESSAGE);
		systemDependency.exit(0);
		return null;
	}

	private void handleClear() {
		conversationClearService.apply();
		systemDependency.println(CLEAR_CONFIRMATION);
	}

	private void handleHistory() {
		systemDependency.println(HISTORY_MESSAGE + conversationSizeService.apply());
	}

	private void handleInstructions() {
		systemDependency.println(INSTRUCTIONS_STATUS
				+ (conversationHasSystemInstructionsService.apply() ? INSTRUCTIONS_LOADED : INSTRUCTIONS_MISSING));
	}

	private void handleQuestion(String question) {
		try {
			systemDependency.println(openRouterClientService.apply(question));
		} catch (Exception e) {
			systemDependency.println(ERROR_PREFIX + e.getMessage());
			e.printStackTrace();
		}
	}
}
