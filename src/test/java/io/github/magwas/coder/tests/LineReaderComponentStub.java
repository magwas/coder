package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.jline.reader.LineReader;

import io.github.magwas.coder.LineReaderComponent;

public class LineReaderComponentStub {
	public static LineReaderComponent stub() throws IOException {
		LineReaderComponent mock = mock(LineReaderComponent.class);
		when(mock.getLineReader()).thenReturn(mock(LineReader.class));
		return mock;
	}
}
