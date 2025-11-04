package io.github.magwas.coder.file.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.file.XMLFileWriterService;

public class XMLFileWriterServiceStub implements XMLFileWriterTestData {
	public static XMLFileWriterService stub() {
		return mock(XMLFileWriterService.class);
	}
}
