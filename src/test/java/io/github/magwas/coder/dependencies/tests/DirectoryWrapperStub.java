package io.github.magwas.coder.dependencies.tests;

import static org.mockito.Mockito.*;

import java.nio.file.Paths;

import io.github.magwas.coder.dependencies.DirectoryWrapper;
import io.github.magwas.coder.file.tests.XMLFileWriterTestData;

public class DirectoryWrapperStub implements XMLFileWriterTestData {
	public static DirectoryWrapper stub() {
		DirectoryWrapper mock = mock(DirectoryWrapper.class);
		when(mock.getCurrentDir()).thenReturn(Paths.get(CURRENT_DIR));
		return mock;
	}
}
