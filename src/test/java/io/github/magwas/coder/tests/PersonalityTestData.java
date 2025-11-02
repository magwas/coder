package io.github.magwas.coder.tests;

import io.github.magwas.coder.PersonalityData;

public interface PersonalityTestData extends PersonalityServiceTestData {
	PersonalityData CODER =
			new PersonalityData(DEFAULT_PERSONALITY_NAME, DEFAULT_MODEL_ID, DEFAULT_INSTRUCTIONS_FILE, true, true);
}
