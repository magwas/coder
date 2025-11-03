package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.OpenRouterClientService;
import io.github.magwas.coder.QuestionHandlerService;
import io.github.magwas.coder.ResponseInfo;
import io.github.magwas.coder.SystemWrapper;
import io.github.magwas.coder.config.ApiKeyConfigService;
import io.github.magwas.konveyor.testing.TestBase;

public class QuestionHandlerServiceTest extends TestBase implements MainLoopTestData, PersonalityTestData {

	@InjectMocks
	private QuestionHandlerService questionHandlerService;

	@Mock
	private OpenRouterClientService openRouterClientService;

	@Mock
	private ApiKeyConfigService apiKeyConfigService;

	@Mock
	SystemWrapper system;

	@Test
	@DisplayName("Normal question processing calls OpenRouterClient")
	void testNormalQuestionProcessing() throws Exception {
		assertEquals("Bearer valid-key", apiKeyConfigService.apply());
		questionHandlerService.apply(CODER, ANY_QUESTION);
		verify(openRouterClientService).apply(anyString(), eq("Bearer valid-key"));
	}

	@Test
	@DisplayName("If there is an error during processing message, statuscode is 500")
	void testProcessingError() throws Exception {
		given(ERROR_STATE);
		ResponseInfo result = questionHandlerService.apply(CODER, ANY_QUESTION);
		assertEquals(500, result.statusCode());
	}

	@Test
	@DisplayName("If there is an error during processing message, reason is the exception message")
	void testProcessingErrorMessage() throws Exception {
		given(ERROR_STATE);
		ResponseInfo result = questionHandlerService.apply(CODER, ANY_QUESTION);
		assertEquals("Simulated error", result.reasoning());
	}
}
