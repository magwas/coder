package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.XMLFileWriterService;

public class XMLFileWriterServiceStub implements XMLFileWriterTestData {
	public static XMLFileWriterService stub() {
		return mock(XMLFileWriterService.class);
	}
}
