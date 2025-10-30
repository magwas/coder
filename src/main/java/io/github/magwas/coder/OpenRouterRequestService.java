package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OpenRouterRequestService {
	@Autowired
	private ObjectMapperComponent objectMapperComponent;

	public String apply(RequestMessageData[] messages) throws JsonProcessingException {
		return objectMapperComponent
				.getObjectMapper()
				.writeValueAsString(
						new RequestDataData(OpenRouterClientConstants.MODEL, messages, new ReasoningData("true")));
	}
}
