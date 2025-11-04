package io.github.magwas.coder.conversation.tests;

import java.util.List;
import java.util.Map;

import io.github.magwas.coder.config.PersonalityData;
import io.github.magwas.coder.config.WorkflowStepData;

public interface PersonalityTestData extends PersonalityServiceTestData {
	PersonalityData CODER = new PersonalityData(
			DEFAULT_PERSONALITY_NAME,
			Map.of("start", new WorkflowStepData(List.of("go"), 1, Map.of(200, "stop", 500, "stop"))),
			Map.of());
}
