
package io.github.magwas.coder;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SystemInstructionsService implements ConfigConstants {
    public String apply() {
        try {
            Path instructionsFile = Path.of(INSTRUCTIONS_FILE);
            return Files.exists(instructionsFile) ? Files.readString(instructionsFile).trim() : "";
        } catch (IOException e) {
            return "";
        }
    }
}
