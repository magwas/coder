package io.github.magwas.coder;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.conversation.ConversationClearService;
import io.github.magwas.coder.conversation.ConversationHasSystemInstructionsService;
import io.github.magwas.coder.conversation.ConversationSetupService;
import io.github.magwas.coder.conversation.ConversationSizeService;

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
	private LineReaderDependency lineReaderDependency;

	@Autowired
	private SystemDependency systemDependency;

	@Autowired
	private ConfigLoadService configLoadService;

	@Autowired
	private PersonalityService personalityService;

	@Autowired
	private QuestionHandlerService questionHandlerService;

	public Void apply() throws Exception {
		configLoadService.apply();
		PersonalityData personality = personalityService.apply("coder");

		LineReader lineReader = lineReaderDependency.lineReader;
		systemDependency.println.accept(PROMPT_MESSAGE);
		conversationSetupService.apply(personality.name());

		while (true) {
			String userInput = consoleInputService.apply(lineReader);
			if (userInput == null) break;
			if (userInput.isEmpty()) continue;
			systemDependency.println.accept(GOT_INPUT);
			switch (userInput.toLowerCase()) {
				case "/clear" -> handleClear();
				case "/history" -> handleHistory();
				case "/instructions" -> handleInstructions();
				default -> questionHandlerService.apply(personality.name(), userInput);
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
}
