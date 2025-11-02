package io.github.magwas.coder;

public interface HTTPConstants {
	int HTTP_SUCCESS = 200;
	String CONTENT_TYPE_HEADER = "Content-Type";
	String APPLICATION_JSON_CONTENT_TYPE = "application/json";
	String AUTHORIZATION_HEADER = "Authorization";
	String ERROR_TEMPLATE = "HTTP error %d: %s";
	String HTTP_REFERER_HEADER = "HTTP-Referer";
	String REFERER_URL = "https://github.com/magwas/konveyor";
	String X_TITLE_HEADER = "X-Title";
	String TITLE_CONTENT = "Konveyor Coder";
}
