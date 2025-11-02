package io.github.magwas.coder;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.config.ApiKeyConfigService;
import io.github.magwas.coder.conversation.ConversationState;

@Service
public class QuestionHandlerService implements ErrorMessages, UIConstants {
	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	OpenRouterClientService openRouterClientService;

	@Autowired
	ConversationState conversationState;

	@Autowired
	OpenRouterRequestService requestService;

	@Autowired
	FileWriterService fileWriterService;

	@Autowired
	ApiKeyConfigService apiKeyConfigService;

	@Autowired
	TimeWrapper timeDependency;

	@Autowired
	ObjectMapperWrapper objectMapperWrapper;

	public ResponseInfo apply(PersonalityData personality, StringBuilder question) {
		try {
			systemDependency.println(GOT_INPUT);
			conversationState.conversationHistory.add(new RequestMessageData("user", question.toString()));
			String requestBody = requestService.apply(personality, conversationState.conversationHistory);
			fileWriterService.apply(FileConstants.REQUEST_DUMP_PATH, requestBody);

			String authHeader = apiKeyConfigService.apply();

			long startTime = timeDependency.currentTimeMillis();
			HttpResponse<String> response = openRouterClientService.sendRequest(requestBody, authHeader);
			long endTime = timeDependency.currentTimeMillis();
			long duration = endTime - startTime;

			if (response.statusCode() == 200) {
				String responseBody = response.body();
				fileWriterService.apply(FileConstants.RESPONSE_DUMP_PATH, responseBody);
				ObjectMapper mapper = objectMapperWrapper.objectMapper;
				OpenRouterResponseData aiResponse = mapper.readValue(responseBody, OpenRouterResponseData.class);
				ChoiceData choice = aiResponse.choices()[0];
				MessageData message = choice.message();
				return new ResponseInfo(
						duration, response.statusCode(), message.reasoning(), message.content(), aiResponse.usage());
			} else {
				return new ResponseInfo(duration, response.statusCode(), response.body(), response.body(), null);
			}
		} catch (IOException | InterruptedException e) {
			return new ResponseInfo(0, 500, e.getMessage(), e.getMessage(), null);
		}
	}
}
