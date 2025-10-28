package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationSetupService {
	@Autowired
	private SystemInstructionsService systemInstructionsService;

	@Autowired
	private SourceCodeReaderService sourceCodeReaderService;

	@Autowired
	private ConversationStateComponent conversationStateComponent;

	public Void apply() {
		String instructions = systemInstructionsService.apply();
		String sourceCode = sourceCodeReaderService.apply();
		conversationStateComponent.hasSystemInstructions = !instructions.isEmpty();
		conversationStateComponent.systemMessage = createSystemMessage(instructions, sourceCode);
		conversationStateComponent.conversationHistory.add(
				new RequestMessageData("system", conversationStateComponent.systemMessage));
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
