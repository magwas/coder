package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.DirectoryComponent;

public class DirectoryComponentStub {
	public static DirectoryComponent stub() {
		return Mockito.mock(DirectoryComponent.class);
	}
}
