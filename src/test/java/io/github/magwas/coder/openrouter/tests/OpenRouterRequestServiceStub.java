package io.github.magwas.coder.openrouter.tests;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.magwas.coder.openrouter.OpenRouterRequestService;

public class OpenRouterRequestServiceStub {
	public static OpenRouterRequestService stub() throws JsonProcessingException {
		OpenRouterRequestService mock = mock(OpenRouterRequestService.class);
		when(mock.apply(any(), any(), any())).thenReturn("{}");
		return mock;
	}
}
