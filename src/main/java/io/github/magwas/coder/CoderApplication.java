
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class CoderApplication implements CommandLineRunner {
    @Autowired private OpenRouterClientService openRouterClientService;

    public static void main(String[] args) {
        SpringApplication.run(CoderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
            OpenRouter AI Client with Spring Boot
            Conversation history maintained across requests
            Commands: '/clear', '/exit', '/history', '/instructions'
            Enter multiline input ending with '.'""");
        
        while (true) {
            System.out.print(">>> ");
            String userInput = readMultilineInput(scanner);
            if (userInput == null) break;
            
            switch (userInput.toLowerCase()) {
                case "/clear" -> handleClear();
                case "/history" -> handleHistory();
                case "/instructions" -> handleInstructions();
                default -> handleQuestion(userInput);
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }

    private String readMultilineInput(Scanner scanner) {
        StringBuilder input = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches("/exit|/quit")) return null;
            if (line.equals(".")) break;
            if (input.length() > 0) input.append("\n");
            input.append(line);
        }
        return input.length() > 0 ? input.toString() : "";
    }

    private void handleClear() {
        openRouterClientService.clearHistory();
        System.out.println("Conversation history cleared.");
    }

    private void handleHistory() {
        System.out.println("History messages: " + openRouterClientService.getHistorySize());
    }

    private void handleInstructions() {
        System.out.println("System instructions: " + 
            (openRouterClientService.hasSystemInstructions() ? "LOADED" : "NOT FOUND"));
    }

    private void handleQuestion(String question) {
        try {
            System.out.println(openRouterClientService.apply(question));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
