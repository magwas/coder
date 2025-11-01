package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import io.github.magwas.coder.OpenRouterClientService;
import io.github.magwas.konveyor.testing.TestBase;

public class OpenRouterClientServiceStub implements MainLoopTestData {
	public static OpenRouterClientService stub() {
		OpenRouterClientService mock = mock(OpenRouterClientService.class);
		try {
			doAnswer(invocation -> {
						if (ERROR_STATE.equals(TestBase.environmentState)) {
							throw new RuntimeException("Simulated error");
						}
						return null;
					})
					.when(mock)
					.sendRequest(anyString(), anyString());
		} catch (Exception ignored) {
		}
		return mock;
	}
}
