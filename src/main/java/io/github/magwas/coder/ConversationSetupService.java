package io.github.magwas.coder;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationSetupService implements ConversationStateConstants {
	@Autowired
	private SystemInstructionsService systemInstructionsService;

	@Autowired
	private SourceCodeReaderService sourceCodeReaderService;

	@Autowired
	private ConversationStateRepository conversationStateRepository;

	@Autowired
	private PersonalityService personalityService;

	public Void apply(String personalityName) {
		String instructions = systemInstructionsService.apply(personalityName);
		String sourceCode = sourceCodeReaderService.apply();
		boolean hasSystemInstructions = !instructions.isEmpty();
		String systemMessage = createSystemMessage(instructions, sourceCode);

		ArrayList<RequestMessageData> history = new ArrayList<>();
		history.add(new RequestMessageData("system", systemMessage));

		conversationStateRepository.save(
				new ConversationStateData(STATE_ID, history, systemMessage, hasSystemInstructions));

		return null;
	}

	private String createSystemMessage(String instructions, String sourceCode) {
		String systemPrompt =
				"From now on, your answer should be a proper xml. Use a root element <root>. Files are in <file name=\"path/name.ext\"> tags. Make sure you escape < and &.\n"
						+ "The answer should not contain anything else but xml.\n"
						+ "By default you write programs in java 21.\n";
		if (!instructions.isEmpty()) {
			systemPrompt += "\n" + instructions + "\n";
		}
		systemPrompt += "\nCurrent code:\n" + sourceCode;
		return systemPrompt;
	}
}
