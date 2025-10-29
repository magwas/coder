package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.ChoiceData;
import io.github.magwas.coder.FileWriterService;
import io.github.magwas.coder.FormattingConstants;
import io.github.magwas.coder.MessageData;
import io.github.magwas.coder.ObjectMapperComponent;
import io.github.magwas.coder.OpenRouterResponseData;
import io.github.magwas.coder.OpenRouterResponseService;
import io.github.magwas.coder.ProcessedFilesData;
import io.github.magwas.coder.XMLFileWriterService;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

public class OpenRouterResponseServiceTest extends TestBase implements OpenRouterResponseTestData {

	@InjectMocks
	private OpenRouterResponseService underTest;

	@Test
	@DisplayName("Process valid response")
	void testProcessValidResponse() throws Exception {
		ObjectMapperComponent mapperComponent = TestUtil.dependency(underTest, ObjectMapperComponent.class);
		ObjectMapper mapperMock = mock(ObjectMapper.class);
		when(mapperComponent.getObjectMapper()).thenReturn(mapperMock);

		FileWriterService fileWriter = TestUtil.dependency(underTest, FileWriterService.class);
		XMLFileWriterService xmlWriter = TestUtil.dependency(underTest, XMLFileWriterService.class);

		when(mapperMock.readValue(RESPONSE_BODY, OpenRouterResponseData.class))
				.thenReturn(new OpenRouterResponseData(
						new ChoiceData[] {new ChoiceData(new MessageData(REASONING, CONTENT))}));
		when(xmlWriter.apply(CONTENT)).thenReturn(new ProcessedFilesData(MODIFIED_FILES, DELETED_FILES));

		String result = underTest.apply(RESPONSE_BODY);

		assertEquals(REASONING + FormattingConstants.SECTION_DIVIDER + FORMATTED_FILES, result);
		verify(fileWriter).apply("target/ai.xml", CONTENT);
	}
}
