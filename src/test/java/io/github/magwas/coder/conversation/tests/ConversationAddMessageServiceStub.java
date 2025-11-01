package io.github.magwas.coder.conversation.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.conversation.ConversationAddMessageService;

public class ConversationAddMessageServiceStub {
	public static ConversationAddMessageService stub() {
		return mock(ConversationAddMessageService.class);
	}
}
