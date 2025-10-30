package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import io.github.magwas.coder.CommandConstants;
import io.github.magwas.coder.ConsoleInputService;
import io.github.magwas.konveyor.testing.TestBase;

public class ConsoleInputServiceStub implements MainLoopTestData {
	public static ConsoleInputService stub() throws IOException {
		ConsoleInputService mock = mock(ConsoleInputService.class);
		when(mock.apply(any()))
				.thenAnswer(invocation -> getInputForState(TestBase.environmentState))
				.thenReturn(null);
		return mock;
	}

	private static String getInputForState(String state) {
		if (state == null) return null;
		switch (state) {
			case EXIT_COMMAND_STATE:
				return null;
			case CLEAR_COMMAND_STATE:
				return CommandConstants.CMD_CLEAR;
			case HISTORY_COMMAND_STATE:
				return CommandConstants.CMD_HISTORY;
			case INSTRUCTIONS_COMMAND_STATE:
				return CommandConstants.CMD_INSTRUCTIONS;
			case NORMAL_INPUT_STATE:
				return ANY_QUESTION;
			case ERROR_STATE:
				return ANY_QUESTION;
			default:
				return "";
		}
	}
}
