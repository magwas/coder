package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.ConversationSizeService;

public class ConversationSizeServiceStub {
	public static ConversationSizeService stub() {
		ConversationSizeService mock = mock(ConversationSizeService.class);
		when(mock.apply()).thenReturn(5);
		return mock;
	}
}
