package io.github.magwas.coder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ConversationStateComponent {
	public final List<RequestMessageData> conversationHistory = new ArrayList<>();
	public String systemMessage;
	public boolean hasSystemInstructions;
}
