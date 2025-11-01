package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.QuestionHandlerService;

public class QuestionHandlerServiceStub {
	public static QuestionHandlerService stub() {
		QuestionHandlerService mock = mock(QuestionHandlerService.class);
		doNothing().when(mock).apply(anyString(), anyString());
		return mock;
	}
}
