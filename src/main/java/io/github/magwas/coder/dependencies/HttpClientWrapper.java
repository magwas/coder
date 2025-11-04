package io.github.magwas.coder.dependencies;

import java.net.http.HttpClient;

import org.springframework.stereotype.Component;

@Component
public class HttpClientWrapper {
	public HttpClient httpClient = HttpClient.newHttpClient();
}
