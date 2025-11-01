package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.magwas.coder.OpenRouterRequestService;

public class OpenRouterRequestServiceStub {
	public static OpenRouterRequestService stub() throws JsonProcessingException {
		OpenRouterRequestService mock = mock(OpenRouterRequestService.class);
		when(mock.apply(anyString(), any())).thenReturn("{}");
		return mock;
	}
}
