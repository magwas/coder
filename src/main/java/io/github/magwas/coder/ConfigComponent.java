
package io.github.magwas.coder;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ConfigComponent {
    public String loadApiKey() throws IOException {
        Path keyFile = Path.of(System.getProperty("user.home"), ".openrouter.key");
        if (!Files.exists(keyFile)) {
            throw new IOException("OpenRouter API key file not found at: " + keyFile);
        }
        return "Bearer " + Files.readString(keyFile).trim();
    }

    public String loadSystemInstructions() {
        try {
            Path instructionsFile = Path.of("INSTRUCTIONS.TXT");
            return Files.exists(instructionsFile) ? Files.readString(instructionsFile).trim() : "";
        } catch (IOException e) {
            return "";
        }
    }
}
