
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoderApplication implements CommandLineRunner {
    @Autowired private OpenRouterClientService openRouterClientService;
    @Autowired private ConsoleInputService consoleInputService;
    @Autowired private ConversationClearService conversationClearService;
    @Autowired private ConversationSizeService conversationSizeService;
    @Autowired private ConversationHasSystemInstructionsService conversationHasSystemInstructionsService;
    @Autowired private ConversationSetupService conversationSetupService;

    public static void main(String[] args) {
        SpringApplication.run(CoderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(UIConstants.PROMPT_MESSAGE);
        conversationSetupService.apply();
        
        while (true) {
            String userInput = consoleInputService.apply();
            if (userInput == null) break;
            if (userInput.isEmpty()) continue;
            
            switch (userInput.toLowerCase()) {
                case CommandConstants.CMD_CLEAR -> handleClear();
                case CommandConstants.CMD_HISTORY -> handleHistory();
                case CommandConstants.CMD_INSTRUCTIONS -> handleInstructions();
                default -> handleQuestion(userInput);
            }
        }
        System.out.println(UIConstants.GOODBYE_MESSAGE);
        System.exit(0);
    }

    private void handleClear() {
        conversationClearService.apply();
        System.out.println(UIConstants.CLEAR_CONFIRMATION);
    }

    private void handleHistory() {
        System.out.println(UIConstants.HISTORY_MESSAGE + conversationSizeService.apply());
    }

    private void handleInstructions() {
        System.out.println(UIConstants.INSTRUCTIONS_STATUS + 
            (conversationHasSystemInstructionsService.apply() ? 
                UIConstants.INSTRUCTIONS_LOADED : 
                UIConstants.INSTRUCTIONS_MISSING));
    }

    private void handleQuestion(String question) {
        try {
            System.out.println(openRouterClientService.apply(question));
        } catch (Exception e) {
            System.out.println(UIConstants.ERROR_PREFIX + e.getMessage());
            e.printStackTrace();
        }
    }
}