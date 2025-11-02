package io.github.magwas.coder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OpenRouterRequestService {
	@Autowired
	ObjectMapperWrapper objectMapperWrapper;

	public String apply(PersonalityData personality, List<RequestMessageData> messages) throws JsonProcessingException {
		return objectMapperWrapper.objectMapper.writeValueAsString(
				new OpenRouterRequestData(personality.modelId(), messages, new ReasoningData(true)));
	}
}
