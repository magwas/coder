package io.github.magwas.coder;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	OpenRouterResponseService responseService;

	public void apply(PersonalityData personality, StringBuilder question) {
		try {
			systemDependency.println.accept(GOT_INPUT);
			conversationState.conversationHistory.add(new RequestMessageData("user", question.toString()));
			question.setLength(0);
			String requestBody = requestService.apply(personality, conversationState.conversationHistory);
			fileWriterService.apply(FileConstants.REQUEST_DUMP_PATH, requestBody);

			String authHeader = apiKeyConfigService.apply();

			long startTime = timeDependency.currentTimeMillis();
			HttpResponse<String> response = openRouterClientService.sendRequest(requestBody, authHeader);
			long endTime = timeDependency.currentTimeMillis();
			long duration = endTime - startTime;

			fileWriterService.apply(FileConstants.RESPONSE_DUMP_PATH, response.body());

			if (response.statusCode() == 200) {
				String result = responseService.apply(response.body(), duration);
				conversationState.conversationHistory.add(new RequestMessageData("assistant", result));
				systemDependency.println.accept(result);
			} else {
				systemDependency.println.accept(String.format(ERROR_TEMPLATE, response.statusCode(), response.body()));
			}
		} catch (Exception e) {
			systemDependency.println.accept(ERROR_PREFIX + e.getMessage());
		}
	}
}
