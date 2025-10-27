 # OpenRouter AI Client

A Spring Boot application that interacts with OpenRouter AI API with conversation history and system instructions.
Setup

    Create an API key file in your home directory:
    bash

echo "your-openrouter-api-key-here" > ~/.openrouter.key

Optional: Create INSTRUCTIONS.TXT in the application directory with system instructions

    This file will be loaded automatically and prepended to all conversations

    The instructions guide the AI's behavior and response style

Build and run:
bash

mvn clean package
java -jar target/coder-1.0.0.jar

Features

    API Key Management: Reads API key from ~/.openrouter.key

    System Instructions: Loads initial instructions from INSTRUCTIONS.TXT if present

    Conversation History: Maintains context across multiple messages

    Interactive REPL: Command-line interface for chatting

    Spring Boot: Dependency injection and easy configuration

Commands

    Type your message to chat with the AI

    /clear - Clear conversation history (keeps system instructions)

    /history - Show number of messages in history

    /instructions - Check if system instructions are loaded

    /exit or /quit - Exit the application

Conversation History

The application maintains conversation history with system instructions as the first message, allowing the AI to maintain consistent behavior and remember previous context.
