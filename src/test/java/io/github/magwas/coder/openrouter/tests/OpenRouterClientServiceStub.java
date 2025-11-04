package io.github.magwas.coder.openrouter.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.http.HttpResponse;

import io.github.magwas.coder.openrouter.OpenRouterClientService;
import io.github.magwas.coder.tests.MainLoopTestData;
import io.github.magwas.konveyor.testing.TestBase;

public class OpenRouterClientServiceStub implements MainLoopTestData, OpenRouterResponseTestData {
	public static OpenRouterClientService stub() throws IOException, InterruptedException {
		OpenRouterClientService mock = mock(OpenRouterClientService.class);
		doAnswer(invocation -> {
					if (ERROR_STATE.equals(TestBase.environmentState)) {
						throw new IOException("Simulated error");
					}
					var response = mock(HttpResponse.class);
					when(response.body()).thenReturn(RESPONSE_BODY);
					return response;
				})
				.when(mock)
				.apply(anyString(), anyString());

		return mock;
	}
}
