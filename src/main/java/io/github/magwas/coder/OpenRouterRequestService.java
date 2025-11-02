package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OpenRouterRequestService {
	@Autowired
	private ObjectMapperWrapper objectMapperWrapper;

	@Autowired
	private PersonalityService personalityService;

	public String apply(String personalityName, RequestMessageData[] messages) throws JsonProcessingException {
		PersonalityData personality = personalityService.apply(personalityName);
		return objectMapperWrapper.objectMapper.writeValueAsString(
				new RequestDataData(personality.modelId(), messages, new ReasoningData(true)));
	}
}
