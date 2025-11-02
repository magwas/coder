package io.github.magwas.coder.tests;

import io.github.magwas.coder.UIConstants;

public interface MainLoopTestData extends UIConstants {
	String EXIT_COMMAND_STATE = "exit";
	String CLEAR_COMMAND_STATE = "clear";
	String HISTORY_COMMAND_STATE = "history";
	String INSTRUCTIONS_COMMAND_STATE = "instructions";
	String NORMAL_INPUT_STATE = "normal";
	String ERROR_STATE = "error";
	String TEST_FAILURE_STATE = "test-failure";
	StringBuilder ANY_QUESTION = new StringBuilder("question");
}
