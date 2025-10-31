package io.github.magwas.coder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenRouterClientService implements ErrorMessages {
	@Autowired
	private OpenRouterRequestService requestService;

	@Autowired
	private OpenRouterResponseService responseService;

	@Autowired
	private ConfigState configState;

	@Autowired
	private ConversationAddMessageService conversationAddMessageService;

	@Autowired
	private ConversationGetMessagesService conversationGetMessagesService;

	@Autowired
	private FileWriterService fileWriterService;

	@Autowired
	private ApiKeyConfigService apiKeyConfigService;

	private final HttpClient httpClient = HttpClient.newHttpClient();

	public String apply(String personalityName, String question) {
		try {
			conversationAddMessageService.apply(new RequestMessageData("user", question));

			String requestBody = requestService.apply(personalityName, conversationGetMessagesService.apply());
			fileWriterService.apply("target/request.dump", requestBody);

			String authHeader = apiKeyConfigService.apply();

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(configState.configData.openrouterUrl()))
					.header("Authorization", authHeader)
					.header("Content-Type", "application/json")
					.header("HTTP-Referer", "https://github.com/magwas/konveyor")
					.header("X-Title", "Konveyor Coder")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			fileWriterService.apply("target/response.dump", response.body());

			if (response.statusCode() == 200) {
				String result = responseService.apply(response.body());
				conversationAddMessageService.apply(new RequestMessageData("assistant", result));
				return result;
			} else {
				return String.format(ERROR_TEMPLATE, response.statusCode(), response.body());
			}
		} catch (Exception e) {
			throw new RuntimeException(API_ERROR, e);
		}
	}
}
