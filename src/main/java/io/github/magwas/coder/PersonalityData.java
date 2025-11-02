package io.github.magwas.coder;

public record PersonalityData(
		String name,
		String modelId,
		String instructionsFile,
		Boolean showReasoning,
        Boolean showContent,
		Boolean writeXML,
		String testCommand,
		Integer testRetries) {
}
