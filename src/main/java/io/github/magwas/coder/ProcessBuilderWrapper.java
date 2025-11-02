package io.github.magwas.coder;

import org.springframework.stereotype.Component;

@Component
public class ProcessBuilderWrapper {
	public ProcessBuilder getBuilder() {
		return new ProcessBuilder();
	}
}
