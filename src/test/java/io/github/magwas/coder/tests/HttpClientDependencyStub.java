package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import java.net.http.HttpClient;

import io.github.magwas.coder.HttpClientDependency;

public class HttpClientDependencyStub {
	public static HttpClientDependency stub() {
		HttpClientDependency mock = new HttpClientDependency();
		mock.httpClient = mock(HttpClient.class);
		return mock;
	}
}
