package io.github.magwas.coder;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LineReaderComponent {
	@Autowired
	private TerminalComponent terminalComponent;

	public final LineReader getLineReader() throws IOException {
		return LineReaderBuilder.builder()
				.terminal(terminalComponent.getTerminal())
				.build();
	}
}
