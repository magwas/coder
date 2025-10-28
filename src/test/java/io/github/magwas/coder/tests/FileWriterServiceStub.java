package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.FileWriterService;

public class FileWriterServiceStub {
	public static FileWriterService stub() {
		return Mockito.mock(FileWriterService.class);
	}
}
