
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationClearService {
    @Autowired private ConversationStateComponent conversationStateComponent;
    
    public Void apply() {
        conversationStateComponent.conversationHistory.clear();
        conversationStateComponent.conversationHistory.add(
            new RequestMessageData("system", conversationStateComponent.systemMessage)
        );
        return null;
    }
}