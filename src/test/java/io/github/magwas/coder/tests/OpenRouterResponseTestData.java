package io.github.magwas.coder.tests;

import java.util.List;

public interface OpenRouterResponseTestData extends XMLFileWriterTestData {
	String RESPONSE_BODY =
			"{\"choices\":[{\"message\":{\"reasoning\":\"Test reasoning\",\"content\":\"<root><file name=\\\"file1.txt\\\">Content1</file><file name=\\\"file2.txt\\\">Content2</file></root>\"}}]}";
	String REASONING = "Test reasoning";
	String CONTENT = VALID_XML_WITH_FILES;
	List<String> MODIFIED_FILES = List.of(FILE_NAME_1, FILE_NAME_2);
	List<String> DELETED_FILES = List.of();
	String FORMATTED_FILES = "Modified files:\n- file1.txt\n- file2.txt\n";
}
