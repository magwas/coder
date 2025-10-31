package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import org.jline.reader.LineReader;

import io.github.magwas.coder.LineReaderDependency;

public class LineReaderDependencyStub {
	public static LineReaderDependency stub() {
		LineReaderDependency mock = mock(LineReaderDependency.class);
		mock.lineReader = mock(LineReader.class);
		return mock;
	}
}
