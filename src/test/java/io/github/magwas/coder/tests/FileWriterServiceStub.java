package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.FileWriterService;

public class FileWriterServiceStub {
	public static FileWriterService stub() {
		FileWriterService mock = mock(FileWriterService.class);
		doNothing().when(mock).apply(any(), any());
		return mock;
	}
}
