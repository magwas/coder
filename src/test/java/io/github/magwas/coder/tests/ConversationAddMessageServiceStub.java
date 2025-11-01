package io.github.magwas.coder.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.ConversationAddMessageService;

public class ConversationAddMessageServiceStub {
	public static ConversationAddMessageService stub() {
		return mock(ConversationAddMessageService.class);
	}
}
