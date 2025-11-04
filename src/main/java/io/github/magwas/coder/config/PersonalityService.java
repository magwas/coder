package io.github.magwas.coder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.ErrorMessages;

@Service
public class PersonalityService {
	@Autowired
	ConfigState configState;

	public PersonalityData apply(String personalityName) {
		if (configState.configData == null || configState.configData.personalities() == null) {
			throw new IllegalStateException(ErrorMessages.CONFIG_NOT_LOADED);
		}

		return configState.configData.personalities().stream()
				.filter(p -> p.name().equals(personalityName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format(ErrorMessages.PERSONALITY_NOT_FOUND, personalityName)));
	}
}
