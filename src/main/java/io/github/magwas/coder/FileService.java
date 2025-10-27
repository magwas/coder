
package io.github.magwas.coder;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Deprecated
@Service
public class FileService {
    public void apply(String content) throws IOException {
        Path path = Path.of(FileConstants.AI_OUTPUT_PATH);
        Files.createDirectories(path.getParent());
        Files.writeString(path, content);
    }
}
