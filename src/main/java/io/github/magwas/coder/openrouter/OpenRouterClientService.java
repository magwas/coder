package io.github.magwas.coder.openrouter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.dependencies.HttpClientWrapper;

@Service
public class OpenRouterClientService implements HTTPConstants {
	@Autowired
	public HttpClientWrapper httpClientDependency;

	@Autowired
	ConfigState configState;

	public HttpResponse<String> apply(String requestBody, String authHeader) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(configState.configData.openrouterUrl()))
				.header(AUTHORIZATION_HEADER, authHeader)
				.header(CONTENT_TYPE_HEADER, APPLICATION_JSON_CONTENT_TYPE)
				.header(HTTP_REFERER_HEADER, REFERER_URL)
				.header(X_TITLE_HEADER, TITLE_CONTENT)
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		return httpClientDependency.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
