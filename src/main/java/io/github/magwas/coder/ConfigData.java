package io.github.magwas.coder;

import java.util.List;

public record ConfigData(String openrouterApiKey, String openrouterUrl, List<PersonalityData> personalities) {}
