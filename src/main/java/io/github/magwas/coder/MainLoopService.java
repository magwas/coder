package io.github.magwas.coder;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.conversation.ConversationSetupService;
import io.github.magwas.coder.conversation.ConversationState;

@Service
public class MainLoopService implements UIConstants, CommandConstants {

	@Autowired
	ConversationSetupService conversationSetupService;

	@Autowired
	ConversationState conversationState;

	@Autowired
	LineReaderWrapper lineReaderDependency;

	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	ConfigLoadService configLoadService;

	@Autowired
	PersonalityService personalityService;

	@Autowired
	QuestionHandlerService questionHandlerService;

	public void apply() throws Exception {
		configLoadService.apply();
		PersonalityData personality = personalityService.apply("coder");

		LineReader lineReader = lineReaderDependency.reader;
		systemDependency.println.accept(PROMPT_MESSAGE);
		conversationSetupService.apply(personality);

		StringBuilder input = new StringBuilder();
		while (true) {
			String line = lineReader.readLine(INPUT_PROMPT);
			if (null == line || CMD_EXIT.equals(line)) break;
			switch (line.split(" ")[0]) {
				case CMD_CLEAR -> handleClear(personality);
				case CMD_HISTORY -> handleHistory();
				case CMD_INSTRUCTIONS -> handleInstructions();
				case MULTILINE_END -> questionHandlerService.apply(personality, input);

				default -> handleLine(input, line);
			}
			systemDependency.println.accept(GOODBYE_MESSAGE);
			systemDependency.exit.accept(0);
		}
	}

	private static void handleLine(StringBuilder input, String line) {
		if (!input.isEmpty()) {
			input.append("\n");
		}
		input.append(line);
	}

	private void handleClear(PersonalityData personality) {
		conversationSetupService.apply(personality);
		systemDependency.println.accept(CLEAR_CONFIRMATION);
	}

	private void handleHistory() {
		systemDependency.println.accept(HISTORY_MESSAGE + conversationState.conversationHistory.size());
	}

	private void handleInstructions() {
		systemDependency.println.accept(INSTRUCTIONS_STATUS
				+ (conversationState.systemMessage != null ? INSTRUCTIONS_LOADED : INSTRUCTIONS_MISSING));
	}
}
