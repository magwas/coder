package io.github.magwas.coder;

import java.io.IOException;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.springframework.stereotype.Component;

@Component
public class TerminalComponent {

	public final Terminal getTerminal() throws IOException {
		return TerminalBuilder.builder().system(true).build();
	}
}
