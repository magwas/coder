package io.github.magwas.coder.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.ConversationGetMessagesService;

public class ConversationGetMessagesServiceStub {
	public static ConversationGetMessagesService stub() {
		return mock(ConversationGetMessagesService.class);
	}
}
