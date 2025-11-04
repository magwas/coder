package io.github.magwas.coder.config.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.config.ApiKeyConfigService;

public class ApiKeyConfigServiceStub {
	public static ApiKeyConfigService stub() {
		ApiKeyConfigService mock = mock(ApiKeyConfigService.class);
		doReturn("Bearer valid-key").when(mock).apply();
		return mock;
	}
}
