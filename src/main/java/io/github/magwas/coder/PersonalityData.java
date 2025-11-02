package io.github.magwas.coder;

public record PersonalityData(
		String name,
		String modelId,
		String instructionsFile,
		Boolean showReasoning,
		Boolean doReasoning,
		Boolean showContent,
		Boolean writeXML,
		String testCommand,
		Integer testRetries,
		Boolean addReply,
		Boolean includeSource) {}
