package io.github.magwas.coder.dependencies.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.dependencies.TimeWrapper;

public class TimeWrapperStub {
	public static TimeWrapper stub() {
		TimeWrapper mock = mock(TimeWrapper.class);
		when(mock.currentTimeMillis()).thenReturn(1000L, 2000L); // Start time, end time
		return mock;
	}
}
