package io.github.magwas.coder;

import java.net.http.HttpClient;

import org.springframework.stereotype.Component;

@Component
public class HttpClientWrapper {
	public HttpClient httpClient = HttpClient.newHttpClient();
}
