package io.github.magwas.coder;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.conversation.ConversationSetupService;

@Service
public class MainLoopService implements UIConstants {

	@Autowired
	CommandHandlerService commandHandlerService;

	@Autowired
	MultilineProcessorService multilineProcessorService;

	@Autowired
	XMLWritingOrchestrationService xmlWritingService;

	@Autowired
	ConversationSetupService conversationSetupService;

	@Autowired
	LineReaderWrapper lineReaderDependency;

	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	ConfigLoadService configLoadService;

	@Autowired
	PersonalityService personalityService;

	@Autowired
	QuestionProcessingService questionProcessingService;

	@Autowired
	ConfigState config;

	public void apply() throws IOException {
		configLoadService.apply();
		PersonalityData personality = initializePersonality();
		conversationSetupService.apply(personality);
		systemDependency.println(personality.prompt());

		StringBuilder input = new StringBuilder();
		while (true) {
			String line = lineReaderDependency.reader.readLine(INPUT_PROMPT);
			if (line == null) break;

			CommandResult commandResult = commandHandlerService.apply(line, personality);
			if (commandResult.shouldExit()) break;
			personality = commandResult.personality();

			MultilineProcessingResult processingResult = multilineProcessorService.apply(line, input, personality);

			if (processingResult.shouldProcess()) {
				processCompleteInput(personality, input);
			}
		}
		systemDependency.println(GOODBYE_MESSAGE);
		systemDependency.exit(0);
	}

	private PersonalityData initializePersonality() {
		String defaultName =
				config.configData.defaultPersonality().isEmpty() ? "coder" : config.configData.defaultPersonality();
		return personalityService.apply(defaultName);
	}

	private void processCompleteInput(PersonalityData personality, StringBuilder input) {
		ResponseInfo result = questionProcessingService.apply(personality, input);
		xmlWritingService.apply(personality, input, result);
		input.setLength(0);
	}
}
