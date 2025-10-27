
package io.github.magwas.coder;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConversationStateComponent {
    public final List<RequestMessageData> conversationHistory = new ArrayList<>();
    public String systemMessage;
    public boolean hasSystemInstructions;
}