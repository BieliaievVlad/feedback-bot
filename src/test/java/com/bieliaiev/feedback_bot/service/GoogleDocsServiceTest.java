package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.exception.GoogleDocsException;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentRequest;
import com.google.api.services.docs.v1.model.Request;

class GoogleDocsServiceTest {

    private Docs mockDocs;
    private Docs.Documents mockDocuments;
    private Docs.Documents.BatchUpdate mockBatchUpdate;

    private GoogleDocsService service;

    @BeforeEach
    void setUp() throws Exception {
        mockDocs = mock(Docs.class);
        mockDocuments = mock(Docs.Documents.class);
        mockBatchUpdate = mock(Docs.Documents.BatchUpdate.class);

        service = new GoogleDocsService("dummy") {
            @Override
            protected Docs initDocsService(String googleCreds) {
                return mockDocs;
            }
            {
                this.documentId = "test-doc-id";
            }
        };

        when(mockDocs.documents()).thenReturn(mockDocuments);
        when(mockDocuments.batchUpdate(anyString(), any(BatchUpdateDocumentRequest.class)))
                .thenReturn(mockBatchUpdate);
        when(mockBatchUpdate.execute()).thenReturn(null);
    }

    @Test
    void appendFeedback_shouldSendCorrectRequest() throws IOException {
        FeedbackDto feedback = new FeedbackDto();
        feedback.setCreatedAt(LocalDateTime.parse("2025-10-15T12:12:12"));
        feedback.setFeedbackText("Test feedback");
        BotUser user = new BotUser();
        user.setPosition("Developer");
        user.setBranch("Kyiv");
        feedback.setBotUser(user);
        FeedbackAnalysis analysis = new FeedbackAnalysis();
        analysis.setCategory("Category1");
        analysis.setLevel(5);
        analysis.setSolution("Solution1");
        feedback.setAnalysis(analysis);

        service.appendFeedback(feedback);

        ArgumentCaptor<BatchUpdateDocumentRequest> captor = ArgumentCaptor.forClass(BatchUpdateDocumentRequest.class);
        verify(mockDocuments).batchUpdate(eq("test-doc-id"), captor.capture());

        BatchUpdateDocumentRequest actualRequest = captor.getValue();
        assertNotNull(actualRequest, "Request should not be null");
        assertEquals(1, actualRequest.getRequests().size(), "There should be exactly one Request");

        Request req = actualRequest.getRequests().get(0);
        String insertedText = req.getInsertText().getText();

        assertTrue(insertedText.contains("2025-10-15T12:12:12"), "Date should be present in the text");
        assertTrue(insertedText.contains("Developer"), "Position should be present in the text");
        assertTrue(insertedText.contains("Kyiv"), "Branch should be present in the text");
        assertTrue(insertedText.contains("Test feedback"), "Feedback text should be present in the text");
        assertTrue(insertedText.contains("Category1"), "Analysis category should be present in the text");
        assertTrue(insertedText.contains(String.valueOf(5)), "Analysis level should be present in the text");
        assertTrue(insertedText.contains("Solution1"), "Analysis solution should be present in the text");
    }

    @Test
    void appendFeedback_shouldThrowGoogleDocsException_onIOException() throws IOException {
        FeedbackDto feedback = new FeedbackDto(1L, 
        															LocalDateTime.parse("2025-10-15T12:12:12"), 
        															new BotUser(), 
        															"text", 
        															new FeedbackAnalysis());
        when(mockDocuments.batchUpdate(anyString(), any())).thenThrow(new IOException("IO error"));

        GoogleDocsException ex = assertThrows(GoogleDocsException.class,
                () -> service.appendFeedback(feedback),
                "GoogleDocsException should be thrown on IOException");

        assertTrue(ex.getMessage().contains("Failed to append feedback"),
                "Exception message should contain error description");
    }
}
