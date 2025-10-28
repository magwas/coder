package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigComponent {
	@Autowired
	private ApiKeyConfigService apiKeyConfigService;

	@Autowired
	private ConsoleInputService consoleInputService;

	public String loadApiKey() throws Exception {
		return apiKeyConfigService.apply();
	}

	public ConsoleInputService getConsoleInputService() {
		return consoleInputService;
	}
}
