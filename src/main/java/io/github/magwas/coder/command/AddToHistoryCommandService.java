package io.github.magwas.coder.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.conversation.ConversationState;
import io.github.magwas.coder.openrouter.RequestMessageData;

@Service
public class AddToHistoryCommandService implements ProcessingStepService {
	@Autowired
	ConversationState conversation;

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() < 2) return new ProcessingContextData(400, "usage: addHistory role", context.meta());
		conversation.history.add(new RequestMessageData(args.get(1), context.content()));
		return context;
	}
}
