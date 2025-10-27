
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

@Service
public class OpenRouterResponseService implements ErrorMessages, FormattingConstants {
    @Autowired private ObjectMapper objectMapper;
    @Autowired private FileService fileService;

    public String apply(String responseBody) throws JsonProcessingException {
        OpenRouterResponseData response = objectMapper.readValue(responseBody, OpenRouterResponseData.class);
        StringBuilder result = new StringBuilder();
        
        if (response.choices() != null && response.choices().length > 0) {
            MessageData message = response.choices()[0].message();
            if (message != null) {
                if (message.reasoning() != null) {
                    result.append(message.reasoning()).append(SECTION_DIVIDER);
                }
                if (message.content() != null) {
                    try {
                        fileService.apply(message.content());
                    } catch (IOException e) {
                        throw new RuntimeException(ErrorMessages.FILE_ERROR, e);
                    }
                }
            }
        }
        return result.toString();
    }
}
