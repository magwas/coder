package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.ErrorMessages;
import io.github.magwas.coder.FileDeletionService;
import io.github.magwas.coder.FileWriterService;
import io.github.magwas.coder.XMLFileWriterService;
import io.github.magwas.konveyor.testing.TestBase;

public class XMLFileWriterServiceTest extends TestBase implements XMLFileWriterTestData {

	@InjectMocks
	private XMLFileWriterService xmlFileWriterService;

	@Mock
	FileWriterService fileWriter;

	@Mock
	FileDeletionService fileDeletion;

	@Test
	@DisplayName("Write files from XML")
	void testWriteFilesFromXml() throws Exception {
		xmlFileWriterService.apply(VALID_XML_WITH_FILES);
		verify(fileWriter).apply("/fake/path/file1.txt", "Content1");
		verify(fileWriter).apply("/fake/path/file2.txt", "Content2");
	}

	@Test
	@DisplayName("Delete files from XML")
	void testDeleteFilesFromXml() throws Exception {
		xmlFileWriterService.apply(VALID_XML_WITH_DELETIONS);
		verify(fileDeletion).apply("/fake/path/file1.txt");
		verify(fileDeletion).apply("/fake/path/file2.txt");
	}

	@Test
	@DisplayName("Path traversal throws exception")
	void testPathTraversalThrows() throws Exception {
		Exception e = assertThrows(RuntimeException.class, () -> xmlFileWriterService.apply(XML_WITH_PATH_TRAVERSAL));
		assertEquals(ErrorMessages.PATH_TRAVERSAL_ERROR, e.getMessage());
	}
}
