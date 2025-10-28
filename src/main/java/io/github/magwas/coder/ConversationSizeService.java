package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationSizeService implements ConversationStateConstants {
	@Autowired
	private ConversationStateRepository conversationStateRepository;

	public Integer apply() {
		return conversationStateRepository
				.findById(STATE_ID)
				.orElseThrow(() -> new IllegalStateException("Conversation not initialized"))
				.conversationHistory()
				.size();
	}
}
