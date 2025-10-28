package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenRouterResponseService implements ErrorMessages, FormattingConstants {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private XMLFileWriterService xmlFileWriterService;

	@Autowired
	private FileWriterService fileWriterService;

	public String apply(String responseBody) throws JsonProcessingException {
		OpenRouterResponseData response = objectMapper.readValue(responseBody, OpenRouterResponseData.class);
		StringBuilder result = new StringBuilder();

		if (response.choices() != null && response.choices().length > 0) {
			MessageData message = response.choices()[0].message();
			if (message != null) {
				if (message.reasoning() != null) {
					result.append(message.reasoning()).append(SECTION_DIVIDER);
				}
				if (message.content() != null) {
					fileWriterService.apply("target/ai.xml", message.content());
					xmlFileWriterService.apply(message.content());
				}
			}
		}
		return result.toString();
	}
}
