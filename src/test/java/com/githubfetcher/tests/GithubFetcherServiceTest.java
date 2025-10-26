 package com.githubfetcher.tests;

import com.githubfetcher.GithubFetcherService;
import com.githubfetcher.GithubFetcherConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.text.MessageFormat;
import java.io.IOException;
import java.net.http.HttpResponse;
import javax.net.ssl.SSLSession;
import java.net.URI;
import java.util.Optional;

public class GithubFetcherServiceTest {
private GithubFetcherService service;
private HttpClientComponentStub httpClientStub;

@BeforeEach
void setUp() {
    httpClientStub = new HttpClientComponentStub();
    service = new GithubFetcherService(httpClientStub);
    
    // Set default mock response for URL parsing tests
    HttpResponse<String> defaultResponse = new HttpResponseStub(TestConstants.MOCK_RESPONSE_BODY, GithubFetcherConstants.HTTP_SUCCESS);
    httpClientStub.setMockResponse(defaultResponse);
}

@Test
@DisplayName("Should successfully parse valid HTTPS GitHub URL")
void testParseGitHubUrl_ValidHttpsUrl() {
    assertDoesNotThrow(() -> service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.VALID_ISSUE_NUMBER));
}

@Test
@DisplayName("Should successfully parse URL with .git suffix")
void testParseGitHubUrl_ValidUrlWithGitSuffix() {
    assertDoesNotThrow(() -> service.apply(TestConstants.VALID_URL_WITH_GIT, TestConstants.VALID_ISSUE_NUMBER));
}

@Test
@DisplayName("Should successfully parse URL with trailing slash")
void testParseGitHubUrl_ValidUrlWithTrailingSlash() {
    assertDoesNotThrow(() -> service.apply(TestConstants.VALID_URL_WITH_SLASH, TestConstants.VALID_ISSUE_NUMBER));
}

@Test
@DisplayName("Should throw IllegalArgumentException for URL missing repository name")
void testParseGitHubUrl_InvalidUrlMissingRepo() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> service.apply(TestConstants.INVALID_URL_MISSING_REPO, TestConstants.VALID_ISSUE_NUMBER));
    String expectedMessage = MessageFormat.format(TestConstants.EXPECTED_MISSING_OWNER_REPO, TestConstants.INVALID_URL_MISSING_REPO);
    assertEquals(expectedMessage, exception.getMessage());
}

@Test
@DisplayName("Should throw IllegalArgumentException for non-GitHub URL")
void testParseGitHubUrl_NonGitHubUrl() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> service.apply(TestConstants.NON_GITHUB_URL, TestConstants.VALID_ISSUE_NUMBER));
    String expectedMessage = MessageFormat.format(TestConstants.EXPECTED_INVALID_GITHUB_URL, TestConstants.NON_GITHUB_URL);
    assertEquals(expectedMessage, exception.getMessage());
}

@Test
@DisplayName("Should throw IllegalArgumentException for null URL")
void testParseGitHubUrl_NullUrl() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> service.apply(null, TestConstants.VALID_ISSUE_NUMBER));
    assertEquals(TestConstants.EXPECTED_NULL_EMPTY_URL, exception.getMessage());
}

@Test
@DisplayName("Should throw IllegalArgumentException for empty URL")
void testParseGitHubUrl_EmptyUrl() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
        () -> service.apply(TestConstants.EMPTY_URL, TestConstants.VALID_ISSUE_NUMBER));
    assertEquals(TestConstants.EXPECTED_NULL_EMPTY_URL, exception.getMessage());
}

@Test
@DisplayName("Should return response body on successful HTTP request")
void testApply_SuccessfulResponse() throws Exception {
    HttpResponse<String> mockResponse = new HttpResponseStub(TestConstants.MOCK_RESPONSE_BODY, GithubFetcherConstants.HTTP_SUCCESS);
    httpClientStub.setMockResponse(mockResponse);
    
    String result = service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.VALID_ISSUE_NUMBER);
    
    assertEquals(TestConstants.MOCK_RESPONSE_BODY, result);
}

@Test
@DisplayName("Should throw IOException on HTTP error response")
void testApply_HttpErrorResponse() {
    HttpResponse<String> mockResponse = new HttpResponseStub(TestConstants.MOCK_ERROR_RESPONSE_BODY, 404);
    httpClientStub.setMockResponse(mockResponse);
    
    IOException exception = assertThrows(IOException.class, 
        () -> service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.VALID_ISSUE_NUMBER));
    
    assertTrue(exception.getMessage().contains("Failed to fetch issue: HTTP 404"));
}

@Test
@DisplayName("Should propagate IOException from HTTP client")
void testApply_HttpClientIOException() {
    IOException expectedException = new IOException("Network error");
    httpClientStub.setThrowIOException(expectedException);
    httpClientStub.setMockResponse(null); // Clear the default mock
    
    IOException exception = assertThrows(IOException.class, 
        () -> service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.VALID_ISSUE_NUMBER));
    
    assertEquals(expectedException, exception);
}

@Test
@DisplayName("Should propagate InterruptedException from HTTP client")
void testApply_HttpClientInterruptedException() {
    InterruptedException expectedException = new InterruptedException("Request interrupted");
    httpClientStub.setThrowInterruptedException(expectedException);
    httpClientStub.setMockResponse(null); // Clear the default mock
    
    InterruptedException exception = assertThrows(InterruptedException.class, 
        () -> service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.VALID_ISSUE_NUMBER));
    
    assertEquals(expectedException, exception);
}

@Test
@DisplayName("Should handle negative issue numbers")
void testApply_NegativeIssueNumber() {
    HttpResponse<String> mockResponse = new HttpResponseStub(TestConstants.MOCK_RESPONSE_BODY, GithubFetcherConstants.HTTP_SUCCESS);
    httpClientStub.setMockResponse(mockResponse);
    
    assertDoesNotThrow(() -> service.apply(TestConstants.VALID_HTTPS_URL, TestConstants.NEGATIVE_ISSUE_NUMBER));
}

}

class HttpResponseStub implements HttpResponse<String> {
private final String body;
private final int statusCode;

public HttpResponseStub(String body, int statusCode) {
    this.body = body;
    this.statusCode = statusCode;
}

@Override
public int statusCode() { return statusCode; }

@Override
public String body() { return body; }

@Override
public java.net.http.HttpHeaders headers() { return java.net.http.HttpHeaders.of(java.util.Map.of(), (k, v) -> true); }

@Override
public URI uri() { return URI.create("https://api.github.com/repos/test/test/issues/1"); }

@Override
public java.net.http.HttpRequest request() { return null; }

@Override
public Optional<HttpResponse<String>> previousResponse() { return Optional.empty(); }

@Override
public Optional<SSLSession> sslSession() { return Optional.empty(); }

@Override
public java.net.http.HttpClient.Version version() { return java.net.http.HttpClient.Version.HTTP_2; }

}
