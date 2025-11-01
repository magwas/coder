package io.github.magwas.coder.conversation.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.conversation.ConversationGetMessagesService;

public class ConversationGetMessagesServiceStub {
	public static ConversationGetMessagesService stub() {
		return mock(ConversationGetMessagesService.class);
	}
}
