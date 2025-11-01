package io.github.magwas.coder.conversation.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.conversation.ConversationClearService;

public class ConversationClearServiceStub {
	public static ConversationClearService stub() {
		return Mockito.mock(ConversationClearService.class);
	}
}
