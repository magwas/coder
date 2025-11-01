package io.github.magwas.coder.tests;

import io.github.magwas.coder.PersonalityData;

public interface PersonalityServiceTestData {
	String PERSONALITY_NOT_FOUND_STATE = "personality-not-found";
	String DEFAULT_PERSONALITY_NAME = "coder";
	String DEFAULT_MODEL_ID = "model";
	String DEFAULT_INSTRUCTIONS_FILE = "instructions.txt";
	PersonalityData DEFAULT_PERSONALITY =
			new PersonalityData(DEFAULT_PERSONALITY_NAME, DEFAULT_MODEL_ID, DEFAULT_INSTRUCTIONS_FILE);
}
