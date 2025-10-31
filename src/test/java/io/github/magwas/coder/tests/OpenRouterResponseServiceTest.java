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
	@DisplayName("Process valid response")
	void testProcessValidResponse() throws Exception {
		String result = underTest.apply(RESPONSE_BODY);
		assertEquals(REASONING + FormattingConstants.SECTION_DIVIDER + FORMATTED_FILES + TOKEN_USAGE, result);
		verify(TestUtil.dependency(underTest, FileWriterService.class)).apply("target/ai.xml", CONTENT);
	}
}
