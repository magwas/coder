package io.github.magwas.coder.tests;

import java.util.List;

public interface OpenRouterResponseTestData {
	String RESPONSE_BODY = "{\"choices\":[{\"message\":{}}]}";
	String REASONING = "Test reasoning";
	String CONTENT = "<root><file name=\"test.txt\">content</file></root>";
	List<String> MODIFIED_FILES = List.of("test.txt");
	List<String> DELETED_FILES = List.of();
	String FORMATTED_FILES = "Modified files:\n- test.txt\n";
}
