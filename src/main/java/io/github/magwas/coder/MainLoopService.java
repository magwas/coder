package io.github.magwas.coder;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainLoopService {
	@Autowired
	private OpenRouterClientService openRouterClientService;

	@Autowired
	private ConsoleInputService consoleInputService;

	@Autowired
	private ConversationClearService conversationClearService;

	@Autowired
	private ConversationSizeService conversationSizeService;

	@Autowired
	private ConversationHasSystemInstructionsService conversationHasSystemInstructionsService;

	@Autowired
	private ConversationSetupService conversationSetupService;

	@Autowired
	private LineReaderDependency lineReaderDependency;

	@Autowired
	private SystemDependency systemDependency;

	@Autowired
	private ConfigLoadService configLoadService;

	@Autowired
	private PersonalityService personalityService;

	public Void apply() throws Exception {
		configLoadService.apply();
		PersonalityData personality = personalityService.apply("coder");

		LineReader lineReader = lineReaderDependency.lineReader;
		systemDependency.println.accept(
				"OpenRouter AI Client with Spring Boot\nConversation history maintained across requests\nCommands: '/clear', '/exit', '/history', '/instructions'\nEnter multiline input ending with '.'");
		conversationSetupService.apply(personality.name());

		while (true) {
			String userInput = consoleInputService.apply(lineReader);
			if (userInput == null) break;
			if (userInput.isEmpty()) continue;
			systemDependency.println.accept("--- got it ---");
			switch (userInput.toLowerCase()) {
				case "/clear" -> handleClear();
				case "/history" -> handleHistory();
				case "/instructions" -> handleInstructions();
				default -> handleQuestion(personality.name(), userInput);
			}
		}
		systemDependency.println.accept("Goodbye!");
		systemDependency.exit.accept(0);
		return null;
	}

	private void handleClear() {
		conversationClearService.apply();
		systemDependency.println.accept("Conversation history cleared.");
	}

	private void handleHistory() {
		systemDependency.println.accept("History messages: " + conversationSizeService.apply());
	}

	private void handleInstructions() {
		systemDependency.println.accept(
				"System instructions: " + (conversationHasSystemInstructionsService.apply() ? "LOADED" : "NOT FOUND"));
	}

	private void handleQuestion(String personalityName, String question) {
		try {
			systemDependency.println.accept(openRouterClientService.apply(personalityName, question));
		} catch (Exception e) {
			systemDependency.println.accept("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
