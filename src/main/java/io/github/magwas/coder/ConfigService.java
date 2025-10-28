package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
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
