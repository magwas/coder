package io.github.magwas.coder;

import java.net.http.HttpClient;

import org.springframework.stereotype.Component;

@Component
public class HttpClientDependency {
	public HttpClient httpClient = HttpClient.newHttpClient();
}
