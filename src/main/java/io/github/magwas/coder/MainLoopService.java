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

	public Void apply() throws Exception {
		LineReader lineReader = lineReaderComponent.getLineReader();
		System.out.println(PROMPT_MESSAGE);
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
		System.out.println(GOODBYE_MESSAGE);
		System.exit(0);
		return null;
	}

	private void handleClear() {
		conversationClearService.apply();
		System.out.println(CLEAR_CONFIRMATION);
	}

	private void handleHistory() {
		System.out.println(HISTORY_MESSAGE + conversationSizeService.apply());
	}

	private void handleInstructions() {
		System.out.println(INSTRUCTIONS_STATUS
				+ (conversationHasSystemInstructionsService.apply() ? INSTRUCTIONS_LOADED : INSTRUCTIONS_MISSING));
	}

	private void handleQuestion(String question) {
		try {
			System.out.println(openRouterClientService.apply(question));
		} catch (Exception e) {
			System.out.println(ERROR_PREFIX + e.getMessage());
			e.printStackTrace();
		}
	}
}
