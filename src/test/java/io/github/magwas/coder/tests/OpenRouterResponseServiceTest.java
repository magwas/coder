package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.coder.FileWriterService;
import io.github.magwas.coder.FormattingConstants;
import io.github.magwas.coder.OpenRouterResponseService;
import io.github.magwas.konveyor.testing.TestBase;

public class OpenRouterResponseServiceTest extends TestBase implements OpenRouterResponseTestData {

	@InjectMocks
	private OpenRouterResponseService underTest;

	@Test
	@DisplayName("Process valid response with timing")
	void testProcessValidResponseWithTiming() throws Exception {
		String result = underTest.apply(RESPONSE_BODY, 1000L);
		assertTrue(result.contains(REASONING + FormattingConstants.SECTION_DIVIDER));
		assertTrue(result.contains(FORMATTED_FILES));
		assertTrue(result.contains(TOKEN_USAGE));
		assertTrue(result.contains("Request time: 1000 ms"));
		assertTrue(result.contains("Tokens per second: 150.00"));
		verify(TestUtil.dependency(underTest, FileWriterService.class)).apply("target/ai.xml", CONTENT);
	}

	@Test
	@DisplayName("Process response with zero duration")
	void testProcessResponseWithZeroDuration() throws Exception {
		String result = underTest.apply(RESPONSE_BODY, 0L);
		assertTrue(result.contains(TOKEN_USAGE));
		assertFalse(result.contains("Tokens per second")); // Should not calculate TPS for zero duration
	}
}
