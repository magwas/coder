package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import io.github.magwas.coder.SystemDependency;

public class SystemDependencyStub {
	public static SystemDependency stub() {
		SystemDependency mock = mock(SystemDependency.class);
		doThrow(new SecurityException("System.exit attempted")).when(mock).exit(anyInt());
		doNothing().when(mock).println(any());
		return mock;
	}
}
