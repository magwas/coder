package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigState;

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
				return verifyWithTestCommand(personality, result);
			}
			return true;
		} catch (Exception e) {
			systemDependency.println(String.format(BAD_XML_PROMPT, e.getMessage()));
			return false;
		}
	}

	private boolean verifyWithTestCommand(PersonalityData personality, ResponseInfo result) {
		String testOutput = runCommandService.apply(personality.testCommand());
		if (!testOutput.isEmpty()) {
			systemDependency.println(testOutput);
			return false;
		}
		return true;
	}
}
