package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.OpenRouterClientService;
import io.github.magwas.coder.QuestionHandlerService;
import io.github.magwas.coder.config.ApiKeyConfigService;
import io.github.magwas.konveyor.testing.TestBase;

public class QuestionHandlerServiceTest extends TestBase implements MainLoopTestData {

	@InjectMocks
	private QuestionHandlerService questionHandlerService;

	@Mock
	private OpenRouterClientService openRouterClientService;

	@Mock
	private ApiKeyConfigService apiKeyConfigService;

	@BeforeEach
	public void setUp() throws Throwable {
		super.setUp();
		when(apiKeyConfigService.apply()).thenReturn("Bearer valid-key");
	}

	@Test
	@DisplayName("Normal question processing calls OpenRouterClient")
	void testNormalQuestionProcessing() throws Exception {
		@SuppressWarnings("unchecked")
		HttpResponse<String> mockResponse = mock(HttpResponse.class);
		when(mockResponse.statusCode()).thenReturn(200);
		when(mockResponse.body()).thenReturn("{}");
		when(openRouterClientService.sendRequest(anyString(), anyString())).thenReturn(mockResponse);

		questionHandlerService.apply("coder", ANY_QUESTION);
		verify(openRouterClientService).sendRequest(anyString(), eq("Bearer valid-key"));
	}

	@Test
	@DisplayName("Error during processing shows error message")
	void testProcessingError() throws Exception {
		when(openRouterClientService.sendRequest(anyString(), anyString()))
				.thenThrow(new RuntimeException("Simulated error"));

		questionHandlerService.apply("coder", ANY_QUESTION);
		verify(questionHandlerService.systemDependency.println).accept(ERROR_PREFIX + "Simulated error");
	}
}
