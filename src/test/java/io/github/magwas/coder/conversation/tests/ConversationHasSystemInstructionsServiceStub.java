package io.github.magwas.coder.conversation.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.conversation.ConversationHasSystemInstructionsService;

public class ConversationHasSystemInstructionsServiceStub {
	public static ConversationHasSystemInstructionsService stub() {
		ConversationHasSystemInstructionsService mock = mock(ConversationHasSystemInstructionsService.class);
		when(mock.apply()).thenReturn(true);
		return mock;
	}
}
