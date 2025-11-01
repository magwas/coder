package io.github.magwas.coder;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ApiKeyConfigService;
import io.github.magwas.coder.conversation.ConversationAddMessageService;
import io.github.magwas.coder.conversation.ConversationGetMessagesService;

@Service
public class QuestionHandlerService implements ErrorMessages, UIConstants {
	@Autowired
	public SystemDependency systemDependency;

	@Autowired
	private OpenRouterClientService openRouterClientService;

	@Autowired
	private ConversationAddMessageService conversationAddMessageService;

	@Autowired
	private ConversationGetMessagesService conversationGetMessagesService;

	@Autowired
	private OpenRouterRequestService requestService;

	@Autowired
	private FileWriterService fileWriterService;

	@Autowired
	private ApiKeyConfigService apiKeyConfigService;

	@Autowired
	private TimeDependency timeDependency;

	@Autowired
	private OpenRouterResponseService responseService;

	public Void apply(String personalityName, String question) {
		try {
			conversationAddMessageService.apply(new RequestMessageData("user", question));

			String requestBody = requestService.apply(personalityName, conversationGetMessagesService.apply());
			fileWriterService.apply(FileConstants.REQUEST_DUMP_PATH, requestBody);

			String authHeader = apiKeyConfigService.apply();

			long startTime = timeDependency.currentTimeMillis();
			HttpResponse<String> response = openRouterClientService.sendRequest(requestBody, authHeader);
			long endTime = timeDependency.currentTimeMillis();
			long duration = endTime - startTime;

			fileWriterService.apply(FileConstants.RESPONSE_DUMP_PATH, response.body());

			if (response.statusCode() == 200) {
				String result = responseService.apply(response.body(), duration);
				conversationAddMessageService.apply(new RequestMessageData("assistant", result));
				systemDependency.println.accept(result);
			} else {
				systemDependency.println.accept(String.format(ERROR_TEMPLATE, response.statusCode(), response.body()));
			}
		} catch (Exception e) {
			systemDependency.println.accept(ERROR_PREFIX + e.getMessage());
		}
		return null;
	}
}
