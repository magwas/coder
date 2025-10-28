package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.FileDeletionService;

public class FileDeletionServiceStub {
	public static FileDeletionService stub() {
		return Mockito.mock(FileDeletionService.class);
	}
}
