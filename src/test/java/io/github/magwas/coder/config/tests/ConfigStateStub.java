package io.github.magwas.coder.config.tests;

import static org.mockito.Mockito.mock;

import io.github.magwas.coder.config.ConfigData;
import io.github.magwas.coder.config.ConfigState;

public class ConfigStateStub {
	public static ConfigState stub() {
		ConfigState mock = mock(ConfigState.class);
		mock.configData = new ConfigData("key", "http://test.url", 3, null);
		return mock;
	}
}
