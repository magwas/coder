package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.TimeDependency;

public class TimeDependencyStub {
	public static TimeDependency stub() {
		TimeDependency mock = mock(TimeDependency.class);
		when(mock.currentTimeMillis()).thenReturn(1000L, 2000L); // Start time, end time
		return mock;
	}
}
