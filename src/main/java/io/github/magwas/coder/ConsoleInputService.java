package io.github.magwas.coder;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Service;

@Service
public class ConsoleInputService implements CommandConstants, UIConstants {
	private final LineReader lineReader;

	public ConsoleInputService() throws IOException {
		Terminal terminal = TerminalBuilder.builder().system(true).build();

		this.lineReader = LineReaderBuilder.builder().terminal(terminal).build();
	}

	public String apply() {
		StringBuilder input = new StringBuilder();
		while (true) {
			String line = lineReader.readLine(INPUT_PROMPT);
			if (line == null || CMD_EXIT.equals(line) || "/quit".equals(line)) {
				return null;
			}
			if (MULTILINE_END.equals(line)) {
				break;
			}
			if (input.length() > 0) {
				input.append("\n");
			}
			input.append(line);
		}
		return input.toString();
	}
}
