package com.bieliaiev.feedback_bot.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAiFeedbackService {

	@Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public FeedbackAnalysis analyzeFeedback(String feedbackText) throws Exception {

    	
    	System.out.println(feedbackText);
    	
        String prompt = """
            Analyze the following feedback:
            "%s"
            
            1. Determine category: negative, neutral, positive.
            2. Determine severity level from 1 (minor issue) to 5 (critical or urgent issue affecting safety or major experience).
            3. Suggest a short solution.
            
            Respond strictly in JSON:
            {"category": "...", "level": ..., "solution": "..."}
            """.formatted(feedbackText);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                },
                "temperature", 0
        );

        String requestBodyJson = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        Map<?, ?> jsonResponse = objectMapper.readValue(responseBody, Map.class);
        Map<?, ?> choiceMessage = (Map<?, ?>) ((Map<?, ?>) ((java.util.List<?>) jsonResponse.get("choices")).get(0)).get("message");
        String content = (String) choiceMessage.get("content");

        System.out.println(objectMapper.readValue(content, FeedbackAnalysis.class));
        return objectMapper.readValue(content, FeedbackAnalysis.class);
    }
}
