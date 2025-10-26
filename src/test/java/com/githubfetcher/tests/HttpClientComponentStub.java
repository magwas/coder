 package com.githubfetcher.tests;

import com.githubfetcher.HttpClientComponent;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class HttpClientComponentStub extends HttpClientComponent {
private HttpResponse<String> mockResponse;
private IOException throwIOException;
private InterruptedException throwInterruptedException;

public void setMockResponse(HttpResponse<String> mockResponse) {
    this.mockResponse = mockResponse;
}

public void setThrowIOException(IOException exception) {
    this.throwIOException = exception;
}

public void setThrowInterruptedException(InterruptedException exception) {
    this.throwInterruptedException = exception;
}

@Override
public HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
    if (throwIOException != null) {
        throw throwIOException;
    }
    if (throwInterruptedException != null) {
        throw throwInterruptedException;
    }
    return mockResponse;
}

@Override
public CompletableFuture<HttpResponse<String>> sendAsync(HttpRequest request) {
    return CompletableFuture.completedFuture(mockResponse);
}

@Override
public HttpClient getClient() {
    return HttpClient.newHttpClient();
}

}
