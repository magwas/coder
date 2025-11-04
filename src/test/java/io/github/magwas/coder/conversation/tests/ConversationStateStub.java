package io.github.magwas.coder.conversation.tests;

import java.util.ArrayList;

import io.github.magwas.coder.conversation.ConversationState;

public class ConversationStateStub {
	public static ConversationState stub() {
		ConversationState conversationState = new ConversationState();
		conversationState.history = new ArrayList<>();
		return conversationState;
	}
}
