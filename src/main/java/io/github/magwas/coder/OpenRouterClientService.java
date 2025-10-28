package io.github.magwas.coder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenRouterClientService {
	@Autowired
	private OpenRouterRequestService requestService;

	@Autowired
	private OpenRouterResponseService responseService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private ConversationAddMessageService conversationAddMessageService;

	@Autowired
	private ConversationGetMessagesService conversationGetMessagesService;

	@Autowired
	private FileWriterService fileWriterService;

	private final HttpClient httpClient = HttpClient.newHttpClient();

	public String apply(String question) {
		try {
			conversationAddMessageService.apply(new RequestMessageData("user", question));

			String requestBody = requestService.apply(conversationGetMessagesService.apply());
			fileWriterService.apply("target/request.dump", requestBody);

			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(OpenRouterClientConstants.API_URL))
					.header(HTTPConstants.AUTHORIZATION, configService.loadApiKey())
					.header(HTTPConstants.CONTENT_TYPE, HTTPConstants.APPLICATION_JSON)
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			fileWriterService.apply("target/response.dump", response.body());

			if (response.statusCode() == HTTPConstants.HTTP_SUCCESS) {
				String result = responseService.apply(response.body());
				conversationAddMessageService.apply(new RequestMessageData("assistant", result));
				return result;
			} else {
				return String.format(HTTPConstants.ERROR_TEMPLATE, response.statusCode(), response.body());
			}
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessages.API_ERROR, e);
		}
	}
}
