package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.conversation.ConversationState;

@Service
public class CommandHandlerService implements CommandConstants, UIConstants {

	@Autowired
	PersonalityManagementService personalityManagementService;

	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	ConversationState conversationState;

	public CommandResult apply(String line, PersonalityData currentPersonality) {
		if (CMD_EXIT.equals(line)) {
			systemDependency.println(GOODBYE_MESSAGE);
			systemDependency.exit(0);
			return new CommandResult(true, currentPersonality);
		}
		if (CMD_CLEAR.equals(line)) {
			conversationState.conversationHistory.clear();
			systemDependency.println(CLEAR_CONFIRMATION);
			return new CommandResult(false, currentPersonality);
		}
		if (line.startsWith(CMD_PERSONALITY)) {
			return handlePersonalityCommand(line, currentPersonality);
		}
		return new CommandResult(false, currentPersonality);
	}

	private CommandResult handlePersonalityCommand(String line, PersonalityData current) {
		String[] parts = line.split(" ");
		if (parts.length != 2) {
			systemDependency.println(PERSONALITY_USAGE);
			return new CommandResult(false, current);
		}
		PersonalityData newPersonality = personalityManagementService.apply(parts[1], current);
		return new CommandResult(false, newPersonality);
	}
}
