package com.bieliaiev.feedback_bot.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.entity.Feedback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.docs.v1.Docs;
import com.google.api.services.docs.v1.DocsScopes;
import com.google.api.services.docs.v1.model.BatchUpdateDocumentRequest;
import com.google.api.services.docs.v1.model.EndOfSegmentLocation;
import com.google.api.services.docs.v1.model.InsertTextRequest;
import com.google.api.services.docs.v1.model.Request;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleDocsService {

    private final Docs docsService;
    private final String documentId = "1nTCdNwkqfdJS0nCpwkewzl7Ks6ipUbkZZ2MEJZ-_GdI";

    public GoogleDocsService() throws IOException, GeneralSecurityException {
        InputStream in = getClass().getResourceAsStream("/google-credentials.json");
        
        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(List.of(DocsScopes.DOCUMENTS, DocsScopes.DRIVE));
        
        docsService = new Docs.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Feedback Bot").build();
    }

    public void appendFeedback(Feedback feedback) throws IOException {

        String formatted = String.format(
                "%s%n%s, %s%n%s%nCategory: %s%nCriticality level: %d%nSolution: %s%n-----------------------------%n",
                feedback.getCreatedAt(),
                feedback.getPosition(),
                feedback.getBranch(),
                feedback.getFeedbackText(),
                feedback.getCategory(),
                feedback.getLevel(),
                feedback.getSolution()
        );

        Request request = new Request()
                .setInsertText(new InsertTextRequest()
                        .setText(formatted)
                        .setEndOfSegmentLocation(new EndOfSegmentLocation()));

        docsService.documents().batchUpdate(documentId,
                new BatchUpdateDocumentRequest().setRequests(List.of(request)))
                .execute();
    }
}
