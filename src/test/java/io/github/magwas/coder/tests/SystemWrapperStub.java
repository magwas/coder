package io.github.magwas.coder.tests;

import static org.mockito.Mockito.mock;

import java.util.function.Consumer;

import io.github.magwas.coder.SystemExitSimulationException;
import io.github.magwas.coder.SystemWrapper;

public class SystemWrapperStub {

	public static Consumer<String> printlnMock;

	@SuppressWarnings("unchecked")
	public static SystemWrapper stub() {
		SystemWrapper mock = new SystemWrapper();
		mock.exit = (Integer n) -> {
			throw new SystemExitSimulationException(n);
		};
		printlnMock = mock(Consumer.class);
		mock.println = printlnMock;
		return mock;
	}
}
