package io.github.magwas.coder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigState;

@Service
public class OpenRouterClientService {
	@Autowired
	public HttpClientDependency httpClientDependency;

	@Autowired
	private ConfigState configState;

	public HttpResponse<String> sendRequest(String requestBody, String authHeader) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(configState.configData.openrouterUrl()))
				.header("Authorization", authHeader)
				.header("Content-Type", "application/json")
				.header("HTTP-Referer", "https://github.com/magwas/konveyor")
				.header("X-Title", "Konveyor Coder")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();

		return httpClientDependency.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}
}
