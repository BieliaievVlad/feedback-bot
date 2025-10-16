package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;

class OpenAiServiceTest {

    private HttpClient mockClient;
    private OpenAiService service;
    private HttpResponse<String> mockResponse;

    @SuppressWarnings("unchecked")
	@BeforeEach
    void setUp() {
        mockClient = mock(HttpClient.class);
        service = new OpenAiService(mockClient, new com.fasterxml.jackson.databind.ObjectMapper());
        mockResponse = mock(HttpResponse.class);
    }

    @SuppressWarnings("unchecked")
	@Test
    void analyzeFeedback_shouldReturnParsedResponse() throws Exception {

        String jsonResponse = """
            {
              "choices": [
                { "message": { "content": "{\\"category\\":\\"neutral\\",\\"level\\":1,\\"solution\\":\\"ok\\"}" } }
              ]
            }
        """;

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        FeedbackAnalysis result = service.analyzeFeedback("test feedback");

        assertEquals("neutral", result.getCategory(), "Category should be parsed correctly");
        assertEquals(1, result.getLevel(), "Level should be parsed correctly");
        assertEquals("ok", result.getSolution(), "Solution should be parsed correctly");
    }
}