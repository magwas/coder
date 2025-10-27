
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenRouterRequestService {
    @Autowired private ObjectMapper objectMapper;

    public String apply(RequestMessageData[] messages) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
            new RequestDataData(OpenRouterClientConstants.MODEL, messages));
    }
}
