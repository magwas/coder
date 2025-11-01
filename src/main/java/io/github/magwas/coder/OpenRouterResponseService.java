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
	private ObjectMapperComponent objectMapperComponent;

	@Autowired
	private XMLFileWriterService xmlFileWriterService;

	@Autowired
	private FileWriterService fileWriterService;

	public String apply(String responseBody, long durationMs)
			throws IOException, ParserConfigurationException, SAXException {
		ObjectMapper mapper = objectMapperComponent.getObjectMapper();
		OpenRouterResponseData response = mapper.readValue(responseBody, OpenRouterResponseData.class);
		StringBuilder result = new StringBuilder();

		if (response.choices() != null && response.choices().length > 0) {
			MessageData message = response.choices()[0].message();
			if (message != null) {
				if (message.reasoning() != null) {
					result.append(message.reasoning()).append(SECTION_DIVIDER);
				}
				if (message.content() != null) {
					fileWriterService.apply("target/ai.xml", message.content());
					ProcessedFilesData processedFiles = xmlFileWriterService.apply(message.content());
					result.append(formatFileChanges(processedFiles));
				}
			}
		}

		if (response.usage() != null) {
			result.append("\nToken usage: ")
					.append(response.usage().prompt_tokens())
					.append(" input, ")
					.append(response.usage().completion_tokens())
					.append(" output");

			// Calculate TPS
			int totalTokens =
					response.usage().prompt_tokens() + response.usage().completion_tokens();
			double seconds = durationMs / 1000.0;
			result.append(String.format("\nRequest time: %d ms", durationMs));
			if (durationMs > 0) {
				double tps = totalTokens * 1000.0 / durationMs;
				result.append(String.format("\nTokens per second: %.2f", tps));
			}
		}

		return result.toString();
	}

	private String formatFileChanges(ProcessedFilesData processedFiles) {
		StringBuilder sb = new StringBuilder();
		if (!processedFiles.modifiedFiles().isEmpty()) {
			sb.append("Modified files:\n");
			processedFiles
					.modifiedFiles()
					.forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		if (!processedFiles.deletedFiles().isEmpty()) {
			sb.append("Deleted files:\n");
			processedFiles.deletedFiles().forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		return sb.toString();
	}
}
