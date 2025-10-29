package io.github.magwas.coder;

import java.io.IOException;

import org.jline.reader.LineReader;
import org.springframework.stereotype.Service;

@Service
public class ConsoleInputService implements CommandConstants, UIConstants {
	public String apply(LineReader lineReader) throws IOException {
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
