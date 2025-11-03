package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.conversation.ConversationState;

@Service
public class CommandHandlingService implements CommandConstants, UIConstants {
	@Autowired
	private PersonalityManagementService personalityManagementService;

	@Autowired
	private SystemWrapper systemDependency;

	@Autowired
	private ConversationState conversationState;

	public CommandResultData apply(String command, PersonalityData currentPersonality) {
		if (CMD_EXIT.equals(command)) {
			systemDependency.println(GOODBYE_MESSAGE);
			systemDependency.exit(0);
			return new CommandResultData(true, currentPersonality);
		}
		if (CMD_CLEAR.equals(command)) {
			conversationState.conversationHistory.clear();
			systemDependency.println(CLEAR_CONFIRMATION);
			return new CommandResultData(false, currentPersonality);
		}
		if (command.startsWith(CMD_PERSONALITY)) {
			String[] parts = command.split(" ");
			if (parts.length != 2) {
				systemDependency.println(PERSONALITY_USAGE);
				return new CommandResultData(false, currentPersonality);
			}
			PersonalityData newPersonality = personalityManagementService.apply(parts[1], currentPersonality);
			return new CommandResultData(false, newPersonality);
		}
		return new CommandResultData(false, currentPersonality);
	}
}
