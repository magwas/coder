package io.github.magwas.coder;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.conversation.ConversationSetupService;

@Service
public class XMLWritingOrchestrationService implements ErrorMessages, UIConstants {

	@Autowired
	XMLFileWriterService xmlFileWriter;

	@Autowired
	RunCommandService runCommandService;

	@Autowired
	ConfigState configState;

	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	QuestionProcessingService questionProcessingService;

	@Autowired
	ConversationSetupService conversationSetupService;

	public void apply(PersonalityData personality, StringBuilder input, ResponseInfo result) {
		if (!personality.writeXML()) return;

		int maxRetries =
				personality.testCommand() != null ? personality.testRetries() : configState.configData.xmlRetries();

		for (int retry = 0; retry < maxRetries; retry++) {
			if (attemptXmlProcessing(personality, result)) break;
		}
	}

	private boolean attemptXmlProcessing(PersonalityData personality, ResponseInfo result) {
		try {
			xmlFileWriter.apply(result.content());
			if (personality.testCommand() != null) {
				for (int i = 0; i <= personality.testRetries(); i++) {
					String testTesult = runCommandService.apply(personality.testCommand());
					if (!testTesult.isEmpty()) {
						conversationSetupService.apply(personality);
						result = questionProcessingService.apply(personality, new StringBuilder(testTesult));
					}
				}
			}
			return true;
		} catch (Exception e) {
			systemDependency.println(MessageFormat.format(BAD_XML_PROMPT, e.getMessage()));
			return false;
		}
	}
}
