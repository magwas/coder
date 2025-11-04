package io.github.magwas.coder.openrouter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.magwas.coder.dependencies.ObjectMapperWrapper;

@Service
public class OpenRouterRequestService {
	@Autowired
	ObjectMapperWrapper objectMapperWrapper;

	public String apply(List<RequestMessageData> messages, String modelId, Boolean doReasoning)
			throws JsonProcessingException {
		return objectMapperWrapper.objectMapper.writeValueAsString(
				new OpenRouterRequestData(modelId, messages, new ReasoningData(doReasoning)));
	}
}
