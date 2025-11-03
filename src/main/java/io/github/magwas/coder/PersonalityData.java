package io.github.magwas.coder;

import java.util.List;

public record PersonalityData(
		String name,
		String modelId,
		List<String> instructionsFile,
		Boolean showReasoning,
		Boolean doReasoning,
		Boolean showContent,
		Boolean writeXML,
		String testCommand,
		Integer testRetries,
		Boolean addReply,
		Boolean includeSource,
		Boolean showUsage,
		String prompt) {}
