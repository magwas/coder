package io.github.magwas.coder.tests;

import org.jline.reader.LineReader;
import org.mockito.Mockito;

import io.github.magwas.coder.LineReaderDependency;

public class LineReaderDependencyStub {
	public static LineReaderDependency stub() {
		LineReaderDependency stub = Mockito.mock(LineReaderDependency.class);
		stub.lineReader = Mockito.mock(LineReader.class);
		return stub;
	}
}
