
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenRouterConversationService {
    private final List<RequestMessageData> conversationHistory = new ArrayList<>();
    private final String systemInstructions;

    @Autowired
    public OpenRouterConversationService(ConfigComponent configComponent) {
        this.systemInstructions = configComponent.loadSystemInstructions();
        if (!systemInstructions.isEmpty()) {
            conversationHistory.add(new RequestMessageData("system", systemInstructions));
        }
    }

    public void addMessage(RequestMessageData message) {
        conversationHistory.add(message);
    }

    public void clear() {
        conversationHistory.clear();
        if (!systemInstructions.isEmpty()) {
            conversationHistory.add(new RequestMessageData("system", systemInstructions));
        }
    }

    public int size() {
        return conversationHistory.size();
    }

    public RequestMessageData[] getMessages() {
        return conversationHistory.toArray(new RequestMessageData[0]);
    }
}
