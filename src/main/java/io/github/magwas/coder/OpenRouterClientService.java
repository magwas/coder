
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenRouterClientService {
    @Autowired private OpenRouterConversationService conversationService;
    @Autowired private OpenRouterRequestService requestService;
    @Autowired private OpenRouterResponseService responseService;
    @Autowired private ConfigComponent configComponent;
    
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String apply(String question) {
        try {
            conversationService.addMessage(new RequestMessageData("user", question));
            
            String requestBody = requestService.createRequestBody(conversationService.getMessages());
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OpenRouterClientConstants.API_URL))
                .header("Authorization", configComponent.loadApiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
            
System.out.println("asking");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println("response:"+response);
System.out.println("body:"+response.body());
            String result = responseService.processResponse(response.body());
            
            conversationService.addMessage(new RequestMessageData("assistant", result));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error calling OpenRouter API", e);
        }
    }
    
    public void clearHistory() {
        conversationService.clear();
    }
    
    public int getHistorySize() {
        return conversationService.size();
    }
    
    public boolean hasSystemInstructions() {
        return !conversationService.getMessages()[0].content().isEmpty();
    }
}
