package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import io.github.magwas.coder.QuestionProcessingService;
import io.github.magwas.coder.ResponseInfo;

public class QuestionProcessingServiceStub {
	public static QuestionProcessingService stub() {
		QuestionProcessingService mock = mock(QuestionProcessingService.class);
		when(mock.apply(any(), any())).thenReturn(new ResponseInfo(0, 200, "", "", null));
		return mock;
	}
}
