package io.github.magwas.coder.conversation.tests;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import io.github.magwas.coder.config.PersonalityService;
import io.github.magwas.konveyor.testing.TestBase;

public class PersonalityServiceStub implements PersonalityTestData {
	public static PersonalityService stub() {
		PersonalityService mock = mock(PersonalityService.class);
		when(mock.apply(anyString())).thenAnswer(invocation -> {
			String personalityName = invocation.getArgument(0);
			if (PERSONALITY_NOT_FOUND_STATE.equals(TestBase.environmentState)) {
				throw new IllegalArgumentException("Personality not found: " + personalityName);
			}
			if (DEFAULT_PERSONALITY_NAME.equals(personalityName)) {
				return CODER;
			}
			throw new IllegalArgumentException("Personality not found: " + personalityName);
		});
		return mock;
	}
}
