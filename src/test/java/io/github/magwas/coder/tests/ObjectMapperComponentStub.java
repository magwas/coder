package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import io.github.magwas.coder.ObjectMapperComponent;

public class ObjectMapperComponentStub {
	public static ObjectMapperComponent stub() {
		return Mockito.mock(ObjectMapperComponent.class);
	}
}
