package io.github.magwas.coder;

import java.util.List;

import org.springframework.data.annotation.Id;

public record ConversationStateData(
		@Id String id,
		List<RequestMessageData> conversationHistory,
		String systemMessage,
		boolean hasSystemInstructions) {
	public static final String SINGLETON_ID = "singleton";
}
