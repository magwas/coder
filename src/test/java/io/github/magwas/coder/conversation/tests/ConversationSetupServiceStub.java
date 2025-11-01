package io.github.magwas.coder.conversation.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.conversation.ConversationSetupService;

public class ConversationSetupServiceStub {
	public static ConversationSetupService stub() {
		return Mockito.mock(ConversationSetupService.class);
	}
}
