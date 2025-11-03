package io.github.magwas.coder.tests;

import java.util.List;

import io.github.magwas.coder.PersonalityData;

public interface PersonalityTestData extends PersonalityServiceTestData {
	PersonalityData CODER = new PersonalityData(
			DEFAULT_PERSONALITY_NAME,
			DEFAULT_MODEL_ID,
			List.of(DEFAULT_INSTRUCTIONS_FILE),
			true,
			true,
			false,
			true,
			"mvn test",
			3,
			true,
			true,
			true,
			"OpenRouter AI Client with Spring Boot\nConversation history maintained across requests\nCommands: '/clear', '/exit', '/history', '/instructions'\nEnter multiline input ending with '.'");
}
