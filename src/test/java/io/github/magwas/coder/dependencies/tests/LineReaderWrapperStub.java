package io.github.magwas.coder.dependencies.tests;

import static org.mockito.Mockito.*;

import org.jline.reader.LineReader;

import io.github.magwas.coder.dependencies.LineReaderWrapper;

public class LineReaderWrapperStub {
	public static LineReaderWrapper stub() {
		LineReaderWrapper mock = mock(LineReaderWrapper.class);
		mock.reader = mock(LineReader.class);
		return mock;
	}
}
