package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.ConversationClearService;

public class ConversationClearServiceStub {
	public static ConversationClearService stub() {
		return Mockito.mock(ConversationClearService.class);
	}
}
