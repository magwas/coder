package io.github.magwas.coder.conversation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.*;

@Service
public class ConversationSetupService {
	@Autowired
	SystemInstructionsService systemInstructionsService;

	@Autowired
	SourceCodeReaderService sourceCodeReaderService;

	@Autowired
	ConversationState conversationState;

	public void apply(PersonalityData personality) {
		String instructions = systemInstructionsService.apply(personality);
		ArrayList<RequestMessageData> history = new ArrayList<>();
		history.add(new RequestMessageData("system", instructions));

		if (Boolean.TRUE.equals(personality.includeSource())) {
			String sourceCode = sourceCodeReaderService.apply();
			history.add(new RequestMessageData("system", UIConstants.CURRENT_CODE_SECTION + sourceCode));
		}

		conversationState.systemMessage = instructions;
		conversationState.conversationHistory = history;
	}
}
