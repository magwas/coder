package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.file.Paths;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;

import io.github.magwas.coder.DirectoryComponent;
import io.github.magwas.coder.FileDeletionService;
import io.github.magwas.coder.FileWriterService;
import io.github.magwas.coder.XMLFileWriterService;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

public class XMLFileWriterServiceTest extends TestBase implements XMLFileWriterTestData {

	@InjectMocks
	private XMLFileWriterService underTest;

	@Test
	@DisplayName("Write files from XML")
	void testWriteFilesFromXml() throws Exception {
		FileWriterService fileWriter = TestUtil.dependency(underTest, FileWriterService.class);
		DirectoryComponent dirComponent = TestUtil.dependency(underTest, DirectoryComponent.class);

		when(dirComponent.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		underTest.apply(VALID_XML_WITH_FILES);

		verify(fileWriter).apply(Paths.get(CURRENT_DIR, FILE_NAME_1).toString(), CONTENT_1);
		verify(fileWriter).apply(Paths.get(CURRENT_DIR, FILE_NAME_2).toString(), CONTENT_2);
		verifyNoInteractions(TestUtil.dependency(underTest, FileDeletionService.class));
	}

	@Test
	@DisplayName("Delete files from XML")
	void testDeleteFilesFromXml() throws Exception {
		FileDeletionService fileDeletion = TestUtil.dependency(underTest, FileDeletionService.class);
		DirectoryComponent dirComponent = TestUtil.dependency(underTest, DirectoryComponent.class);

		when(dirComponent.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		underTest.apply(VALID_XML_WITH_DELETIONS);

		verify(fileDeletion).apply(Paths.get(CURRENT_DIR, FILE_NAME_1).toString());
		verify(fileDeletion).apply(Paths.get(CURRENT_DIR, FILE_NAME_2).toString());
		verifyNoInteractions(TestUtil.dependency(underTest, FileWriterService.class));
	}

	@Test
	@DisplayName("Path traversal throws exception")
	void testPathTraversalThrows() throws Exception {
		DirectoryComponent dirComponent = TestUtil.dependency(underTest, DirectoryComponent.class);

		when(dirComponent.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		Exception e = assertThrows(RuntimeException.class, () -> underTest.apply(XML_WITH_PATH_TRAVERSAL));

		assertEquals("Path traversal attempt detected", e.getMessage());
		verifyNoInteractions(TestUtil.dependency(underTest, FileWriterService.class));
		verifyNoInteractions(TestUtil.dependency(underTest, FileDeletionService.class));
	}
}
