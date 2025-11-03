package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.conversation.ConversationSetupService;

@Service
public class PersonalityManagementService implements ErrorMessages, UIConstants {

	@Autowired
	PersonalityService personalityService;

	@Autowired
	ConversationSetupService conversationSetupService;

	@Autowired
	SystemWrapper systemDependency;

	public PersonalityData apply(String name, PersonalityData current) {
		try {
			PersonalityData newPersonality = personalityService.apply(name);
			conversationSetupService.apply(newPersonality);
			systemDependency.println(String.format(PERSONALITY_CHANGED, name));
			systemDependency.println(newPersonality.prompt());
			return newPersonality;
		} catch (IllegalArgumentException e) {
			systemDependency.println(String.format(PERSONALITY_NOT_FOUND, name));
			return current;
		}
	}
}
