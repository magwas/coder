package io.github.magwas.coder.conversation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationClearService {
	@Autowired
	private ConversationStateRepository conversationStateRepository;

	public Void apply() {
		var state = conversationStateRepository
				.findById(ConversationStateData.SINGLETON_ID)
				.orElseGet(() ->
						new ConversationStateData(ConversationStateData.SINGLETON_ID, new ArrayList<>(), "", false));
		state = new ConversationStateData(
				ConversationStateData.SINGLETON_ID, new ArrayList<>(), state.systemMessage(), false);
		conversationStateRepository.save(state);
		return null;
	}
}
