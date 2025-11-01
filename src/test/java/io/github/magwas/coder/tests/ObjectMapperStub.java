package io.github.magwas.coder.tests;

import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.ObjectMapperComponent;

public class ObjectMapperStub {
	public static ObjectMapperComponent stub() {
		ObjectMapperComponent stub = Mockito.mock(ObjectMapperComponent.class);
		Mockito.when(stub.getObjectMapper()).thenReturn(Mockito.mock(ObjectMapper.class));
		return stub;
	}
}
