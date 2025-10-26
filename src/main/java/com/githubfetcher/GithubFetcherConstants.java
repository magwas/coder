 package com.githubfetcher;

public interface GithubFetcherConstants {
String GITHUB_API_BASE_URL = "https://api.github.com/repos/";
String ACCEPT_HEADER = "application/vnd.github.v3+json";
String USER_AGENT = "GithubFetcherService/1.0";
int HTTP_SUCCESS = 200;
String API_URL_FORMAT = "{0}{1}/{2}/issues/{3}";
String EXCEPTION_NULL_EMPTY_URL = "Repository URL cannot be null or empty";
String EXCEPTION_INVALID_GITHUB_URL = "Invalid GitHub repository URL: {0}";
String EXCEPTION_MISSING_OWNER_REPO = "GitHub URL must include both owner and repository name: {0}";
String EXCEPTION_HTTP_ERROR = "Failed to fetch issue: HTTP {0} - {1}";
}
