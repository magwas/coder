package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import io.github.magwas.coder.ErrorMessages;
import io.github.magwas.coder.ProcessedFilesData;
import io.github.magwas.coder.XMLFileWriterService;
import io.github.magwas.konveyor.testing.TestBase;

public class XMLFileWriterServiceTest extends TestBase implements XMLFileWriterTestData {

	@InjectMocks
	private XMLFileWriterService xmlFileWriterService;

	@Test
	@DisplayName("Write files from XML")
	void testWriteFilesFromXml() throws Exception {
		ProcessedFilesData result = xmlFileWriterService.apply(VALID_XML_WITH_FILES);
		assertIterableEquals(List.of(FILE_NAME_1, FILE_NAME_2), result.modifiedFiles());
		assertTrue(result.deletedFiles().isEmpty());
	}

	@Test
	@DisplayName("Delete files from XML")
	void testDeleteFilesFromXml() throws Exception {
		ProcessedFilesData result = xmlFileWriterService.apply(VALID_XML_WITH_DELETIONS);
		assertIterableEquals(List.of(FILE_NAME_1, FILE_NAME_2), result.deletedFiles());
		assertTrue(result.modifiedFiles().isEmpty());
	}

	@Test
	@DisplayName("Path traversal throws exception")
	void testPathTraversalThrows() throws Exception {
		Exception e = assertThrows(RuntimeException.class, () -> xmlFileWriterService.apply(XML_WITH_PATH_TRAVERSAL));
		assertEquals(ErrorMessages.PATH_TRAVERSAL_ERROR, e.getMessage());
	}
}
