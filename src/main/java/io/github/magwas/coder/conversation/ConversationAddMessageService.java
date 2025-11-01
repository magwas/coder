package io.github.magwas.coder.conversation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.RequestMessageData;

@Service
public class ConversationAddMessageService implements ConversationStateConstants {
	@Autowired
	private ConversationStateRepository conversationStateRepository;

	public Void apply(RequestMessageData message) {
		ConversationStateData state = conversationStateRepository
				.findById(STATE_ID)
				.orElseThrow(() -> new IllegalStateException("Conversation not initialized"));

		ArrayList<RequestMessageData> newHistory = new ArrayList<>(state.conversationHistory());
		newHistory.add(message);

		conversationStateRepository.save(
				new ConversationStateData(STATE_ID, newHistory, state.systemMessage(), state.hasSystemInstructions()));

		return null;
	}
}
