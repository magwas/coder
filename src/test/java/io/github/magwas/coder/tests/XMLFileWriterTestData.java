package io.github.magwas.coder.tests;

import java.util.List;

public interface XMLFileWriterTestData {
	String VALID_XML_WITH_FILES =
			"<root><file name=\"file1.txt\">Content1</file><file name=\"file2.txt\">Content2</file></root>";
	String VALID_XML_WITH_DELETIONS = "<root><deleted name=\"file1.txt\"/><deleted name=\"file2.txt\"/></root>";
	String XML_WITH_PATH_TRAVERSAL = "<root><file name=\"../outside.txt\">Content</file></root>";
	String FILE_NAME_1 = "file1.txt";
	String FILE_NAME_2 = "file2.txt";
	String[] MODIFIED_FILE_NAMES = {FILE_NAME_1, FILE_NAME_2};
	String[] DELETED_FILE_NAMES = {FILE_NAME_1, FILE_NAME_2};
	String CONTENT_1 = "Content1";
	String CONTENT_2 = "Content2";
	String PATH_TRAVERSAL_NAME = "../outside.txt";
	String CURRENT_DIR = "/fake/path";
	String ERROR_MESSAGE = "Test error";
	List<String> MODIFIED_FILES = List.of(MODIFIED_FILE_NAMES);
	List<String> DELETED_FILES = List.of(DELETED_FILE_NAMES);
}
