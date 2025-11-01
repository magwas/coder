# Konveyor Coder

AI-powered code assistant using OpenRouter API with Spring Boot.

## Features

- Interactive console interface with command history
- Maintains conversation context across requests
- Processes AI responses as XML for file operations
- Tracks token usage and request timing
- Configurable personalities with system instructions
- File operation safety checks (path traversal prevention)

## Prerequisites

- Java 21 JDK
- OpenRouter API key

## Installation

1. Download coder-x.y.z.jar from maven central

2. Create config file at `~/.coder/coder.conf`:
   ```json
   {
     "openrouterApiKey": "your-api-key-here",
     "openrouterUrl": "https://openrouter.ai/api/v1/chat",
     "personalities": [
       {
         "name": "coder",
         "modelId": "openai/gpt-4",
         "instructionsFile": "system_instructions.txt"
       }
     ]
   }
   ```

3. Place system instructions in `~/.coder/system_instructions.txt`

## Usage

```bash
cd <project directory>
java -jar coder-x.y.z.jar
```

Interactive commands:
- `/clear` - Reset conversation history
- `/history` - Show number of messages in conversation
- `/instructions` - Show system instructions status
- `/exit` or `/quit` - Exit the program

## Configuration

### coder.conf
- `openrouterApiKey`: Your OpenRouter API key
- `openrouterUrl`: OpenRouter API endpoint
- `personalities`: Array of personality configurations

### Personality Configuration
- `name`: Personality identifier
- `modelId`: OpenRouter model ID
- `instructionsFile`: Filename of system instructions in ~/.coder

## File Operations

AI responses must be valid XML containing file operations:
```xml
<root>
  <file name="path/to/file.java">// Java code here</file>
  <deleted name="path/to/delete.txt"/>
</root>
```

## Security

- All file operations are restricted to project directory
- Path traversal attempts are blocked
- API key stored in user-specific config file

## Contributing

Contributions welcome! Please open an issue first to discuss proposed changes.

## License

GNU Affero General Public License
