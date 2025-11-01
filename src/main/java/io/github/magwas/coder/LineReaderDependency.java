package io.github.magwas.coder;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

@Component
public class LineReaderDependency {
	public LineReader lineReader;

	public LineReaderDependency() {
		try {
			Terminal terminal = TerminalBuilder.builder().system(true).build();
			this.lineReader = LineReaderBuilder.builder().terminal(terminal).build();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create LineReader", e);
		}
	}
}
