package io.github.magwas.coder.dependencies.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.dependencies.SystemWrapper;

public class SystemWrapperStub {

	public static SystemWrapper stub() {
		SystemWrapper mock = mock(SystemWrapper.class);
		doAnswer(invocation -> new SystemExitSimulationException(invocation.getArgument(0)))
				.when(mock)
				.exit(anyInt());
		return mock;
	}
}
