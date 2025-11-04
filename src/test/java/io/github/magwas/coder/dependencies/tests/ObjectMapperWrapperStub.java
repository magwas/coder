package io.github.magwas.coder.dependencies.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.dependencies.ObjectMapperWrapper;
import io.github.magwas.coder.openrouter.ChoiceData;
import io.github.magwas.coder.openrouter.MessageData;
import io.github.magwas.coder.openrouter.OpenRouterResponseData;
import io.github.magwas.coder.openrouter.UsageData;
import io.github.magwas.coder.openrouter.tests.OpenRouterResponseTestData;

public class ObjectMapperWrapperStub implements OpenRouterResponseTestData {
	public static ObjectMapperWrapper stub() {
		ObjectMapperWrapper wrapper = new ObjectMapperWrapper();
		try {
			Field objectMapperField = ObjectMapperWrapper.class.getDeclaredField("objectMapper");
			objectMapperField.setAccessible(true);
			ObjectMapper objectMapperMock = mock(ObjectMapper.class);
			when(objectMapperMock.readValue(eq(RESPONSE_BODY), eq(OpenRouterResponseData.class)))
					.thenReturn(new OpenRouterResponseData(
							new ChoiceData[] {new ChoiceData(new MessageData(REASONING, CONTENT))},
							new UsageData(PROMPT_TOKENS, COMPLETION_TOKENS)));
			objectMapperField.set(wrapper, objectMapperMock);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return wrapper;
	}
}
