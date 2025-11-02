package io.github.magwas.coder;

public record PersonalityData(
		String name, String modelId, String instructionsFile, Boolean showReasoning, Boolean writeXML) {}
