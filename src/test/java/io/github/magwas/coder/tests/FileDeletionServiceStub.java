package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.FileDeletionService;

public class FileDeletionServiceStub {
	public static FileDeletionService stub() {
		FileDeletionService mock = mock(FileDeletionService.class);
		doNothing().when(mock).apply(any());
		return mock;
	}
}
