package io.github.magwas.coder;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

@Component
public class LineReaderComponent {
	public final LineReader getLineReader() throws IOException {
		Terminal terminal = TerminalBuilder.builder().system(true).build();
		return LineReaderBuilder.builder().terminal(terminal).build();
	}
}
