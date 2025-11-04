package io.github.magwas.coder.dependencies.tests;

import static org.mockito.Mockito.*;

import java.net.http.HttpClient;

import io.github.magwas.coder.dependencies.HttpClientWrapper;

public class HttpClientWrapperStub {
	public static HttpClientWrapper stub() {
		HttpClientWrapper mock = new HttpClientWrapper();
		mock.httpClient = mock(HttpClient.class);
		return mock;
	}
}
