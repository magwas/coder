package io.github.magwas.coder.command;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.UIConstants;
import io.github.magwas.coder.conversation.ConversationState;

@Service
public class ClearCommandService implements ProcessingStepService, UIConstants {
	@Autowired
	ConversationState conversationState;

	public ProcessingContextData apply(ProcessingContextData contextData, List<String> args) {
		if (args.size() != 1) return new ProcessingContextData(400, "usage: clear", contextData.meta());

		conversationState.history.clear();
		return new ProcessingContextData(200, CLEAR_CONFIRMATION, Map.of());
	}
}
