package io.github.magwas.coder;

import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.conversation.ConversationSetupService;
import io.github.magwas.coder.conversation.ConversationState;

@Service
public class MainLoopService implements ErrorMessages, UIConstants, CommandConstants {

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

	@Autowired
	FileWriterService fileWriterService;

	@Autowired
	XMLFileWriterService xmlFileWriter;

	@Autowired
	ConfigState config;

	@Autowired
	RunCommandService runCommandService;

	@Autowired
	QuestionProcessingService questionProcessingService;

	public void apply() throws IOException {
		configLoadService.apply();
		String defaultName = config.configData.defaultPersonality() != null
						&& !config.configData.defaultPersonality().isEmpty()
				? config.configData.defaultPersonality()
				: "coder";
		PersonalityData personality = personalityService.apply(defaultName);

		LineReader lineReader = lineReaderDependency.reader;
		systemDependency.println(personality.prompt());
		conversationSetupService.apply(personality);

		StringBuilder input = new StringBuilder();
		while (true) {
			String line = lineReader.readLine(INPUT_PROMPT);
			if (null == line || CMD_EXIT.equals(line)) break;
			String[] commandLine = line.split(" ");
			switch (commandLine[0]) {
				case CMD_CLEAR -> handleClear(personality);
				case CMD_HISTORY -> handleHistory();
				case CMD_INSTRUCTIONS -> handleInstructions();
				case CMD_PERSONALITY -> {
					if (commandLine.length > 1) {
						personality = handlePersonality(commandLine[1], personality);
					} else {
						systemDependency.println(PERSONALITY_USAGE);
					}
				}
				case MULTILINE_END -> askAi(personality, input);
				default -> handleLine(input, line);
			}
		}
		systemDependency.println(GOODBYE_MESSAGE);
		systemDependency.exit(0);
	}

	private PersonalityData handlePersonality(String name, PersonalityData personality) {
		try {
			PersonalityData newPersonality = personalityService.apply(name);
			conversationSetupService.apply(newPersonality);
			systemDependency.println(String.format(PERSONALITY_CHANGED, name));
			systemDependency.println(newPersonality.prompt());
			return newPersonality;
		} catch (IllegalArgumentException e) {
			systemDependency.println(String.format(PERSONALITY_NOT_FOUND, name));
		}

		return personality;
	}

	private void askAi(PersonalityData personality, StringBuilder input) {
		ResponseInfo result = questionProcessingService.apply(personality, input);
		writeXml(personality, input, result);
	}

	private void writeXml(PersonalityData personality, StringBuilder input, ResponseInfo result) {
		if (personality.writeXML()) {
			int maxRetries = (personality.testCommand() != null
							&& !personality.testCommand().isEmpty())
					? personality.testRetries()
					: config.configData.xmlRetries();

			for (int retry = 0; retry < maxRetries; retry++) {
				boolean xmlSuccess = false;
				for (int xmlRetry = 0; xmlRetry < config.configData.xmlRetries(); xmlRetry++) {
					Exception e = writeFiles(result);
					if (e == null) {
						xmlSuccess = true;
						break;
					}
					if (!e.getClass().equals(SAXException.class)) {
						systemDependency.println(e.getMessage());
					}
					input.append(MessageFormat.format(BAD_XML_PROMPT, e.getMessage()));
					systemDependency.println(input.toString());
					result = questionProcessingService.apply(personality, input);
				}

				if (!xmlSuccess) break;

				if (personality.testCommand() != null
						&& !personality.testCommand().isEmpty()) {
					String testOutput = runTestCommand(personality.testCommand());
					if (testOutput.isEmpty()) {
						break;
					} else {
						conversationSetupService.apply(personality);
						systemDependency.println(testOutput);
						input.append("\n").append(testOutput);
						result = questionProcessingService.apply(personality, input);
					}
				} else {
					break;
				}
			}
		}
	}

	private String runTestCommand(String testCommand) {
		try {
			String output = runCommandService.apply(testCommand);
			return output.isEmpty() ? "" : output;
		} catch (RuntimeException e) {
			return e.getMessage();
		}
	}

	private Exception writeFiles(ResponseInfo result) {
		try {
			xmlFileWriter.apply(result.content());
			return null;
		} catch (ParserConfigurationException | IOException | SAXException e) {
			return e;
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
		systemDependency.println(CLEAR_CONFIRMATION);
	}

	private void handleHistory() {
		systemDependency.println(HISTORY_MESSAGE + conversationState.conversationHistory.size());
	}

	private void handleInstructions() {
		systemDependency.println(INSTRUCTIONS_STATUS
				+ (conversationState.systemMessage != null ? INSTRUCTIONS_LOADED : INSTRUCTIONS_MISSING));
	}
}
