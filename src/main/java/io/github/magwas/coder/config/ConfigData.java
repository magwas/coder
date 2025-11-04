package io.github.magwas.coder.config;

import java.util.List;

public record ConfigData(
		String openrouterApiKey,
		String openrouterUrl,
		List<PersonalityData> personalities,
		String defaultPersonality) {}
