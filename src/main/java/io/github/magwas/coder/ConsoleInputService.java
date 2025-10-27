
package io.github.magwas.coder;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ConsoleInputService implements CommandConstants, UIConstants {
    private final LineReader lineReader;

    public ConsoleInputService() throws IOException {
        Terminal terminal = TerminalBuilder.builder()
            .system(true)
            .build();
        
        this.lineReader = LineReaderBuilder.builder()
            .terminal(terminal)
            .build();
    }

    public String apply() {
        StringBuilder input = new StringBuilder();
        while (true) {
            String line = lineReader.readLine(INPUT_PROMPT);
            if (line == null || line.equals(CMD_EXIT) || line.equals("/quit")) {
                return null;
            }
            if (line.equals(MULTILINE_END)) {
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
