package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.XMLFileWriterService;

public class XMLFileWriterServiceStub {
	public static XMLFileWriterService stub() {
		return Mockito.mock(XMLFileWriterService.class);
	}
}
