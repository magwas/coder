package io.github.magwas.coder.tests;

import static org.mockito.Mockito.mock;

import java.util.function.Consumer;

import io.github.magwas.coder.SystemDependency;
import io.github.magwas.coder.SystemExitSimulationException;

public class SystemDependencyStub {

	public static Consumer<String> printlnMock;

	@SuppressWarnings("unchecked")
	public static SystemDependency stub() {
		SystemDependency mock = new SystemDependency();
		mock.exit = (Integer n) -> {
			throw new SystemExitSimulationException(n);
		};
		printlnMock = mock(Consumer.class);
		mock.println = printlnMock;
		return mock;
	}
}
