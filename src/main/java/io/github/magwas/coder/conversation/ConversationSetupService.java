package io.github.magwas.coder.conversation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.PersonalityService;
import io.github.magwas.coder.RequestMessageData;
import io.github.magwas.coder.SourceCodeReaderService;
import io.github.magwas.coder.SystemInstructionsService;
import io.github.magwas.coder.UIConstants;

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
		String systemPrompt = UIConstants.SYSTEM_PROMPT;
		if (!instructions.isEmpty()) {
			systemPrompt += "\n" + instructions + "\n";
		}
		systemPrompt += UIConstants.CURRENT_CODE_SECTION + sourceCode;
		return systemPrompt;
	}
}
