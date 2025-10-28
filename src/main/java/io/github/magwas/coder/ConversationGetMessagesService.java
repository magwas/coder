package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationGetMessagesService implements ConversationStateConstants {
	@Autowired
	private ConversationStateRepository conversationStateRepository;

	public RequestMessageData[] apply() {
		return conversationStateRepository
				.findById(STATE_ID)
				.orElseThrow(() -> new IllegalStateException("Conversation not initialized"))
				.conversationHistory()
				.toArray(new RequestMessageData[0]);
	}
}
