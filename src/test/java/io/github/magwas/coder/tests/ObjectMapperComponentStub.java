package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.ChoiceData;
import io.github.magwas.coder.MessageData;
import io.github.magwas.coder.ObjectMapperComponent;
import io.github.magwas.coder.OpenRouterResponseData;
import io.github.magwas.coder.UsageData;

public class ObjectMapperComponentStub implements OpenRouterResponseTestData {
	public static ObjectMapperComponent stub() {
		ObjectMapperComponent mock = mock(ObjectMapperComponent.class);
		ObjectMapper objectMapperMock = mock(ObjectMapper.class);
		try {
			when(objectMapperMock.readValue(RESPONSE_BODY, OpenRouterResponseData.class))
					.thenReturn(new OpenRouterResponseData(
							new ChoiceData[] {new ChoiceData(new MessageData(REASONING, CONTENT))},
							new UsageData(PROMPT_TOKENS, COMPLETION_TOKENS)));
		} catch (Exception ignored) {
		}
		when(mock.getObjectMapper()).thenReturn(objectMapperMock);
		return mock;
	}
}
