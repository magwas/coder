package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.config.ApiKeyConfigService;

public class ApiKeyConfigServiceStub {
	public static ApiKeyConfigService stub() {
		ApiKeyConfigService mock = mock(ApiKeyConfigService.class);
		when(mock.apply()).thenReturn("Bearer valid-key");
		return mock;
	}
}
