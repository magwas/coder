package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import io.github.magwas.coder.ProcessedFilesData;
import io.github.magwas.coder.XMLFileWriterService;

public class XMLFileWriterServiceStub implements XMLFileWriterTestData {
	public static XMLFileWriterService stub() {
		XMLFileWriterService mock = mock(XMLFileWriterService.class);
		try {
			when(mock.apply(VALID_XML_WITH_FILES)).thenReturn(new ProcessedFilesData(MODIFIED_FILES, List.of()));
			when(mock.apply(VALID_XML_WITH_DELETIONS)).thenReturn(new ProcessedFilesData(List.of(), DELETED_FILES));
		} catch (Exception ignored) {
		}
		return mock;
	}
}
