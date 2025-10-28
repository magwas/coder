package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationHasSystemInstructionsService implements ConversationStateConstants {
	@Autowired
	private ConversationStateRepository conversationStateRepository;

	public Boolean apply() {
		return conversationStateRepository
				.findById(STATE_ID)
				.orElseThrow(() -> new IllegalStateException("Conversation not initialized"))
				.hasSystemInstructions();
	}
}
