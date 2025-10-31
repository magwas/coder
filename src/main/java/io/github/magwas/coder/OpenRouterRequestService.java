package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OpenRouterRequestService {
	@Autowired
	private ObjectMapperComponent objectMapperComponent;

	@Autowired
	private PersonalityService personalityService;

	public String apply(String personalityName, RequestMessageData[] messages) throws JsonProcessingException {
		PersonalityData personality = personalityService.apply(personalityName);
		return objectMapperComponent
				.getObjectMapper()
				.writeValueAsString(new RequestDataData(personality.modelId(), messages, new ReasoningData(true)));
	}
}
