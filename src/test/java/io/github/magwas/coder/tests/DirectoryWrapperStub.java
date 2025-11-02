package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import java.nio.file.Paths;

import io.github.magwas.coder.DirectoryWrapper;

public class DirectoryWrapperStub implements XMLFileWriterTestData {
	public static DirectoryWrapper stub() {
		DirectoryWrapper mock = mock(DirectoryWrapper.class);
		when(mock.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		return mock;
	}
}
