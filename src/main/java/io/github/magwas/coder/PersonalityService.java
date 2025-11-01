package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.config.ConfigState;

@Service
public class PersonalityService {
	@Autowired
	private ConfigState configState;

	public PersonalityData apply(String personalityName) {
		if (configState.configData == null || configState.configData.personalities() == null) {
			throw new IllegalStateException("Config not loaded");
		}

		return configState.configData.personalities().stream()
				.filter(p -> p.name().equals(personalityName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Personality not found: " + personalityName));
	}
}
