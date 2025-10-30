package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.ConversationHasSystemInstructionsService;

public class ConversationHasSystemInstructionsServiceStub {
	public static ConversationHasSystemInstructionsService stub() {
		ConversationHasSystemInstructionsService mock = mock(ConversationHasSystemInstructionsService.class);
		when(mock.apply()).thenReturn(true);
		return mock;
	}
}
