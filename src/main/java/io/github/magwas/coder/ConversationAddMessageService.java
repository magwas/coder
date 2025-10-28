package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationAddMessageService {
	@Autowired
	private ConversationStateComponent conversationStateComponent;

	public Void apply(RequestMessageData message) {
		conversationStateComponent.conversationHistory.add(message);
		return null;
	}
}
