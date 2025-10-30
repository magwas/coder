package io.github.magwas.coder.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import io.github.magwas.coder.CommandConstants;
import io.github.magwas.coder.ConsoleInputService;
import io.github.magwas.konveyor.testing.TestBase;

public class ConsoleInputServiceStub implements MainLoopTestData {
	public static ConsoleInputService stub() {
		ConsoleInputService mock = mock(ConsoleInputService.class);
		try {
			when(mock.apply(any())).thenAnswer(invocation -> {
				switch (TestBase.environmentState) {
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
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return mock;
	}
}
