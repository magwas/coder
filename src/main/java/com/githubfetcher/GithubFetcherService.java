 package com.githubfetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.text.MessageFormat;

@Service
public class GithubFetcherService {
private final HttpClientComponent httpClientComponent;

@Autowired
public GithubFetcherService(HttpClientComponent httpClientComponent) {
    this.httpClientComponent = httpClientComponent;
}

public String apply(String repoUrl, int issueNumber) throws IOException, InterruptedException {
    String[] parts = parseGitHubUrl(repoUrl);
    String owner = parts[0];
    String repo = parts[1];
    
    String encodedOwner = URLEncoder.encode(owner, StandardCharsets.UTF_8);
    String encodedRepo = URLEncoder.encode(repo, StandardCharsets.UTF_8);
    String apiUrl = MessageFormat.format(GithubFetcherConstants.API_URL_FORMAT, 
        GithubFetcherConstants.GITHUB_API_BASE_URL, encodedOwner, encodedRepo, String.valueOf(issueNumber));
    
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(apiUrl))
        .header("Accept", GithubFetcherConstants.ACCEPT_HEADER)
        .header("User-Agent", GithubFetcherConstants.USER_AGENT)
        .GET()
        .build();
    
    HttpResponse<String> response = httpClientComponent.send(request);
    
    if (response.statusCode() != GithubFetcherConstants.HTTP_SUCCESS) {
        String errorMessage = MessageFormat.format(GithubFetcherConstants.EXCEPTION_HTTP_ERROR, 
            String.valueOf(response.statusCode()), response.body());
        throw new IOException(errorMessage);
    }
    
    return response.body();
}

private String[] parseGitHubUrl(String repoUrl) {
    if (repoUrl == null || repoUrl.trim().isEmpty()) {
        throw new IllegalArgumentException(GithubFetcherConstants.EXCEPTION_NULL_EMPTY_URL);
    }
    
    String url = repoUrl.endsWith("/") ? repoUrl.substring(0, repoUrl.length() - 1) : repoUrl;
    String[] urlParts = url.split("github\\.com/");
    
    if (urlParts.length < 2) {
        String errorMessage = MessageFormat.format(GithubFetcherConstants.EXCEPTION_INVALID_GITHUB_URL, repoUrl);
        throw new IllegalArgumentException(errorMessage);
    }
    
    String path = urlParts[1];
    String[] pathParts = path.split("/");
    
    if (pathParts.length < 2) {
        String errorMessage = MessageFormat.format(GithubFetcherConstants.EXCEPTION_MISSING_OWNER_REPO, repoUrl);
        throw new IllegalArgumentException(errorMessage);
    }
    
    String owner = pathParts[0];
    String repo = pathParts[1];
    
    if (repo.endsWith(".git")) {
        repo = repo.substring(0, repo.length() - 4);
    }
    
    return new String[]{owner, repo};
}

}
