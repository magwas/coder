package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.ConversationSetupService;

public class ConversationSetupServiceStub {
	public static ConversationSetupService stub() {
		return Mockito.mock(ConversationSetupService.class);
	}
}
