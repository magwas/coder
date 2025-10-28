package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationGetMessagesService {
	@Autowired
	private ConversationStateComponent conversationStateComponent;

	public RequestMessageData[] apply() {
		return conversationStateComponent.conversationHistory.toArray(new RequestMessageData[0]);
	}
}
