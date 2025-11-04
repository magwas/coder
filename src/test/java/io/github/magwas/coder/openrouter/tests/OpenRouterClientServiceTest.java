package io.github.magwas.coder.openrouter.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.http.HttpRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.dependencies.HttpClientWrapper;
import io.github.magwas.coder.openrouter.OpenRouterClientService;
import io.github.magwas.konveyor.testing.TestBase;

public class OpenRouterClientServiceTest extends TestBase {

	@InjectMocks
	private OpenRouterClientService openRouterClientService;

	@Mock
	private ConfigState configState;

	@Mock
	private HttpClientWrapper httpClientDependency;

	@Test
	@DisplayName("Content-Type header is set to application/json")
	void testContentTypeHeader() throws Exception {
		openRouterClientService.apply("{}", "Bearer key");

		ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
		verify(httpClientDependency.httpClient).send(requestCaptor.capture(), any());
		HttpRequest capturedRequest = requestCaptor.getValue();

		String contentTypeHeader =
				capturedRequest.headers().firstValue("Content-Type").orElse("Header not found");
		assertEquals("application/json", contentTypeHeader);
	}
}
