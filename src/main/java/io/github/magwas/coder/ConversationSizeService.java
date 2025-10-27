
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationSizeService {
    @Autowired private ConversationStateComponent conversationStateComponent;
    
    public Integer apply() {
        return conversationStateComponent.conversationHistory.size();
    }
}