package io.github.magwas.coder.config.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.config.ConfigState;

public class ConfigStateStub {
	public static ConfigState stub() {
		return mock(ConfigState.class);
	}
}
