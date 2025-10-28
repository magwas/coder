package io.github.magwas.coder;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenRouterResponseService implements ErrorMessages, FormattingConstants {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private XMLFileWriterService xmlFileWriterService;

	@Autowired
	private FileWriterService fileWriterService;

	public String apply(String responseBody) throws IOException, ParserConfigurationException, SAXException {
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
