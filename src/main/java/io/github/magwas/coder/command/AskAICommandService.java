package io.github.magwas.coder.command;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.*;
import io.github.magwas.coder.config.ApiKeyConfigService;
import io.github.magwas.coder.conversation.ConversationState;
import io.github.magwas.coder.dependencies.ObjectMapperWrapper;
import io.github.magwas.coder.dependencies.SystemWrapper;
import io.github.magwas.coder.dependencies.TimeWrapper;
import io.github.magwas.coder.file.FileConstants;
import io.github.magwas.coder.file.FileWriterService;
import io.github.magwas.coder.openrouter.*;

@Service
public class AskAICommandService implements ProcessingStepService {

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

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() < 3) return new ProcessingContextData(500, "usage: askAi modelId doReasoning", context.meta());
		String modelId = args.get(1);
		Boolean doReasoning = Boolean.getBoolean(args.get(2));

		Map<String, String> meta = new HashMap<>();

		try {
			systemDependency.println(UIConstants.GOT_INPUT);
			conversationState.history.add(new RequestMessageData("user", context.content()));
			String requestBody = requestService.apply(conversationState.history, modelId, doReasoning);
			fileWriterService.apply(FileConstants.REQUEST_DUMP_PATH, requestBody);

			String authHeader = apiKeyConfigService.apply();

			long startTime = timeDependency.currentTimeMillis();
			HttpResponse<String> response1 = openRouterClientService.apply(requestBody, authHeader);
			long endTime = timeDependency.currentTimeMillis();
			long duration = endTime - startTime;

			meta.put("duration", Long.toString(duration));

			if (response1.statusCode() == 200) {
				String responseBody = response1.body();
				fileWriterService.apply(FileConstants.RESPONSE_DUMP_PATH, responseBody);
				ObjectMapper mapper = objectMapperWrapper.objectMapper;
				OpenRouterResponseData aiResponse = mapper.readValue(responseBody, OpenRouterResponseData.class);
				ChoiceData choice = aiResponse.choices()[0];
				MessageData message = choice.message();
				meta.put("reasoning", message.reasoning());
				return new ProcessingContextData(200, message.content(), meta);
			} else {
				return new ProcessingContextData(response1.statusCode(), response1.body(), meta);
			}
		} catch (IOException | InterruptedException e) {
			return new ProcessingContextData(500, e.getMessage(), meta);
		}
	}
}
