 package com.githubfetcher;

import org.springframework.stereotype.Component;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class HttpClientComponent {
private final HttpClient client;

public HttpClientComponent() {
    this.client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
}

public HttpResponse<String> send(HttpRequest request) throws IOException, InterruptedException {
    return client.send(request, HttpResponse.BodyHandlers.ofString());
}

public CompletableFuture<HttpResponse<String>> sendAsync(HttpRequest request) {
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
}

public HttpClient getClient() {
    return client;
}

}
