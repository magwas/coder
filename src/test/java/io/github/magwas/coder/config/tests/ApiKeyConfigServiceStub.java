package io.github.magwas.coder.config.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.config.ApiKeyConfigService;

public class ApiKeyConfigServiceStub {
	public static ApiKeyConfigService stub() {
		return mock(ApiKeyConfigService.class);
	}
}
