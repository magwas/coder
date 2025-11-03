package io.github.magwas.coder.config;

import java.util.List;

import io.github.magwas.coder.PersonalityData;

public record ConfigData(
		String openrouterApiKey,
		String openrouterUrl,
		Integer xmlRetries,
		List<PersonalityData> personalities,
		String defaultPersonality) {}
