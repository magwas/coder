package io.github.magwas.coder;

import org.springframework.stereotype.Component;

@Component
public class SystemDependency {
	public void exit(int status) {
		System.exit(status);
	}

	public void println(String message) {
		System.out.println(message);
	}
}
