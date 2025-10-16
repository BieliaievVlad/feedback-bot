package com.bieliaiev.feedback_bot.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.exception.GoogleDocsException;
import com.bieliaiev.feedback_bot.utils.StaticStrings;
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

	@Value("${google.document.id}")
	protected String documentId;
    protected Docs docsService;
    
	public GoogleDocsService(@Value("${google.creds.path}") String googleCreds) {
		this.docsService = initDocsService(googleCreds);
	}
	
	protected Docs initDocsService (String googleCreds) {
		try (InputStream in = getClass().getResourceAsStream(googleCreds)) {

			if (in == null) {
				throw new GoogleDocsException("Google credentials file not found: " + googleCreds);
			}

			GoogleCredentials credentials = GoogleCredentials.fromStream(in)
					.createScoped(List.of(DocsScopes.DOCUMENTS, DocsScopes.DRIVE));

			return new Docs.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName(StaticStrings.GOOGLE_APP_NAME)
                    .build();

		} catch (IOException | GeneralSecurityException e) {
			throw new GoogleDocsException("Failed to initialize Google Docs service", e);
		}
	}
	

    public void appendFeedback(FeedbackDto feedback) throws IOException {

    	try {
            String formatted = String.format(
                    StaticStrings.GOOGLE_DOC_TEXT_FORMAT,
                    feedback.getCreatedAt(),
                    feedback.getBotUser().getPosition(),
                    feedback.getBotUser().getBranch(),
                    feedback.getFeedbackText(),
                    feedback.getAnalysis().getCategory(),
                    feedback.getAnalysis().getLevel(),
                    feedback.getAnalysis().getSolution()
            );

            Request request = new Request()
                    .setInsertText(new InsertTextRequest()
                            .setText(formatted)
                            .setEndOfSegmentLocation(new EndOfSegmentLocation()));

            docsService.documents().batchUpdate(documentId,
                    new BatchUpdateDocumentRequest().setRequests(List.of(request)))
                    .execute();
            
		} catch (IOException e) {
			throw new GoogleDocsException("Failed to append feedback", e);
		}
    }
}
