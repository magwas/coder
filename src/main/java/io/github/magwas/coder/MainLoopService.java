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
		systemDependency.println.accept(PROMPT_MESSAGE);
		conversationSetupService.apply();

		while (true) {
			String userInput = consoleInputService.apply(lineReader);
			if (userInput == null) break;
			if (userInput.isEmpty()) continue;
			systemDependency.println.accept(GOT_INPUT);
			switch (userInput.toLowerCase()) {
				case CommandConstants.CMD_CLEAR -> handleClear();
				case CommandConstants.CMD_HISTORY -> handleHistory();
				case CommandConstants.CMD_INSTRUCTIONS -> handleInstructions();
				default -> handleQuestion(userInput);
			}
		}
		systemDependency.println.accept(GOODBYE_MESSAGE);
		systemDependency.exit.accept(0);
		return null;
	}

	private void handleClear() {
		conversationClearService.apply();
		systemDependency.println.accept(CLEAR_CONFIRMATION);
	}

	private void handleHistory() {
		systemDependency.println.accept(HISTORY_MESSAGE + conversationSizeService.apply());
	}

	private void handleInstructions() {
		systemDependency.println.accept(INSTRUCTIONS_STATUS
				+ (conversationHasSystemInstructionsService.apply() ? INSTRUCTIONS_LOADED : INSTRUCTIONS_MISSING));
	}

	private void handleQuestion(String question) {
		try {
			systemDependency.println.accept(openRouterClientService.apply(question));
		} catch (Exception e) {
			systemDependency.println.accept(ERROR_PREFIX + e.getMessage());
			e.printStackTrace();
		}
	}
}
