package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import io.github.magwas.coder.RunCommandService;
import io.github.magwas.konveyor.testing.TestBase;

public class RunCommandServiceStub implements MainLoopTestData {
	public static RunCommandService stub() {
		RunCommandService mock = mock(RunCommandService.class);
		when(mock.apply(anyString())).thenAnswer(invocation -> {
			if (ERROR_STATE.equals(TestBase.environmentState)) {
				throw new RuntimeException("Simulated command error");
			}
			return "Command executed successfully";
		});
		return mock;
	}
}
