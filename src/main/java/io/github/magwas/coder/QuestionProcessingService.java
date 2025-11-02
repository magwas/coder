package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.conversation.ConversationState;

@Service
public class QuestionProcessingService implements UIConstants, FileConstants, ErrorMessages {
	@Autowired
	private QuestionHandlerService questionHandlerService;

	@Autowired
	private SystemWrapper systemDependency;

	@Autowired
	private FileWriterService fileWriterService;

	@Autowired
	private ConversationState conversationState;

	public ResponseInfo apply(PersonalityData personality, StringBuilder input) {
		ResponseInfo result = questionHandlerService.apply(personality, input);
		printUsage(result);
		fileWriterService.apply(AI_OUTPUT_PATH, result.content());

		if (result.statusCode() == 200) {
			conversationState.conversationHistory.add(new RequestMessageData("user", input.toString()));
			if (personality.showReasoning()) {
				systemDependency.println(result.reasoning());
			}
			if (personality.showContent()) {
				systemDependency.println(result.content());
			}
			if (Boolean.TRUE.equals(personality.addReply())) {
				conversationState.conversationHistory.add(new RequestMessageData("assistant", result.content()));
			}
		} else {
			systemDependency.println(String.format(ERROR_TEMPLATE, result.statusCode(), result.reasoning()));
		}
		input.setLength(0);
		return result;
	}

	private void printUsage(ResponseInfo result) {
		StringBuilder usage = new StringBuilder();
		if (result.usage() != null) {
			usage.append("\nToken usage: ")
					.append(result.usage().prompt_tokens())
					.append(" input, ")
					.append(result.usage().completion_tokens())
					.append(" output");

			int totalTokens = result.usage().prompt_tokens() + result.usage().completion_tokens();
			long duration = result.duration();
			usage.append(String.format("\nRequest time: %d ms", duration));
			if (duration > 0) {
				double tps = totalTokens * 1000.0 / duration;
				usage.append(String.format("\nTokens per second: %.2f", tps));
			}
		}
		systemDependency.println(usage.toString());
	}
}
