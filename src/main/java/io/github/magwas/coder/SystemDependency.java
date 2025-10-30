package io.github.magwas.coder;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class SystemDependency {
	public Consumer<Integer> exit;
	public Consumer<String> println;

	SystemDependency() {
		exit = System::exit;
		println = System.out::println;
	}
}
