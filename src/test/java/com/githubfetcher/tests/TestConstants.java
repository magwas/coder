 package com.githubfetcher.tests;

public interface TestConstants {
String VALID_HTTPS_URL = "https://github.com/spring-projects/spring-framework";
String VALID_URL_WITH_GIT = "https://github.com/spring-projects/spring-framework.git";
String VALID_URL_WITH_SLASH = "https://github.com/spring-projects/spring-framework/";
String INVALID_URL_MISSING_REPO = "https://github.com/spring-projects";
String NON_GITHUB_URL = "https://gitlab.com/user/repo";
String EMPTY_URL = "";
int VALID_ISSUE_NUMBER = 1;
int NEGATIVE_ISSUE_NUMBER = -1;
String EXPECTED_MISSING_OWNER_REPO = "GitHub URL must include both owner and repository name: {0}";
String EXPECTED_INVALID_GITHUB_URL = "Invalid GitHub repository URL: {0}";
String EXPECTED_NULL_EMPTY_URL = "Repository URL cannot be null or empty";
String MOCK_RESPONSE_BODY = """
{"id": 1, "title": "Test Issue", "body": "Test body"}""";
String MOCK_ERROR_RESPONSE_BODY = """
{"message": "Not Found", "documentation_url": "https://docs.github.com/rest/reference/issues#get-an-issue"}""";
}
