package io.github.magwas.coder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.ErrorMessages;

@Service
public class ApiKeyConfigService implements ErrorMessages {
	@Autowired
	private ConfigState configState;

	public String apply() {
		if (configState.configData == null) {
			throw new IllegalStateException(ErrorMessages.API_KEY_ERROR);
		}
		return "Bearer " + configState.configData.openrouterApiKey();
	}
}
