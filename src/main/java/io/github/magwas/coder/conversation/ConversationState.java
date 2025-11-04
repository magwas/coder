package io.github.magwas.coder.conversation;

import java.util.List;

import org.springframework.stereotype.Component;

import io.github.magwas.coder.openrouter.RequestMessageData;

@Component
public class ConversationState {
	public List<RequestMessageData> history;
}
