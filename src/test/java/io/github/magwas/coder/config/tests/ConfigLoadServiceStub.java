package io.github.magwas.coder.config.tests;

import static org.mockito.Mockito.*;

import java.io.IOException;

import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.konveyor.testing.TestBase;

public class ConfigLoadServiceStub implements ConfigLoadServiceTestData {
	public static ConfigLoadService stub() {
		ConfigLoadService mock = mock(ConfigLoadService.class);
		try {
			doAnswer(invocation -> {
						if (CONFIG_LOAD_ERROR_STATE.equals(TestBase.environmentState)) {
							throw new IOException("Simulated config load error");
						}
						return null;
					})
					.when(mock)
					.apply();
		} catch (Exception ignored) {
		}
		return mock;
	}
}
