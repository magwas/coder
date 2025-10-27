
package io.github.magwas.coder;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApiKeyConfigService implements ConfigConstants, ErrorMessages {
    public String apply() throws IOException {
        Path keyFile = Path.of(API_KEY_PATH);
        if (!Files.exists(keyFile)) {
            throw new IOException(String.format(API_KEY_ERROR, keyFile));
        }
        return BEARER_PREFIX + Files.readString(keyFile).trim();
    }
}
