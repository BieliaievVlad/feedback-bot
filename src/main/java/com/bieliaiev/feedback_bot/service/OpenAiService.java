package com.bieliaiev.feedback_bot.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.exception.OpenAiException;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.Message;
import com.bieliaiev.feedback_bot.model.OpenAiRequest;
import com.bieliaiev.feedback_bot.utils.StaticStrings;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenAiService {

	@Value("${openai.api.key}")
    private String apiKey;
	private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FeedbackAnalysis analyzeFeedback(String feedbackText) {
    	
    	try {
            String prompt = String.format(StaticStrings.OPEN_AI_PROMPT, feedbackText);

            OpenAiRequest requestPayload = new OpenAiRequest(
                StaticStrings.OPEN_AI_MODEL,
                new Message[]{new Message("user", prompt)},
                0
            );

            String requestBodyJson = objectMapper.writeValueAsString(requestPayload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(StaticStrings.OPEN_AI))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new OpenAiException("OpenAI API error: " + response.body());
            }

            String content = objectMapper.readTree(response.body())
                                         .path("choices").get(0)
                                         .path("message").path("content")
                                         .asText();

            return objectMapper.readValue(content, FeedbackAnalysis.class);
            
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new OpenAiException("Failed to analyze feedback", e);
		}
    }
}
