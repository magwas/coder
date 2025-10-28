package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationHasSystemInstructionsService {
	@Autowired
	private ConversationStateComponent conversationStateComponent;

	public Boolean apply() {
		return conversationStateComponent.hasSystemInstructions;
	}
}
