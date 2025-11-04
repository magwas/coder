package io.github.magwas.coder.file.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.file.FileDeletionService;

public class FileDeletionServiceStub {
	public static FileDeletionService stub() {
		FileDeletionService mock = mock(FileDeletionService.class);
		doNothing().when(mock).apply(any());
		return mock;
	}
}
