package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.SystemExitSimulationException;
import io.github.magwas.coder.SystemWrapper;

public class SystemWrapperStub {

	public static SystemWrapper stub() {
		SystemWrapper mock = mock(SystemWrapper.class);
		doAnswer(invocation -> new SystemExitSimulationException(invocation.getArgument(0)))
				.when(mock)
				.exit(anyInt());
		return mock;
	}
}
