package io.github.magwas.coder.dependencies;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

@Component
public class LineReaderWrapper {
	public LineReader reader;

	public LineReaderWrapper() {
		try {
			Terminal terminal = TerminalBuilder.builder().system(true).build();
			this.reader = LineReaderBuilder.builder().terminal(terminal).build();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create LineReader", e);
		}
	}
}
