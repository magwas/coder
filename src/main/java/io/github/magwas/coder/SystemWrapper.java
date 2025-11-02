package io.github.magwas.coder;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class SystemWrapper {
	public Consumer<Integer> exit;
	public Consumer<String> println;

	public SystemWrapper() {
		exit = System::exit;
		println = System.out::println;
	}
}
