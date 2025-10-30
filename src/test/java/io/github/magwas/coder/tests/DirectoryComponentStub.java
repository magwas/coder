package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import java.nio.file.Paths;

import io.github.magwas.coder.DirectoryComponent;

public class DirectoryComponentStub implements XMLFileWriterTestData {
	public static DirectoryComponent stub() {
		DirectoryComponent mock = mock(DirectoryComponent.class);
		when(mock.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		return mock;
	}
}
