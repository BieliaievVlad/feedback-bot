package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.handler.TelegramMessageHandler;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;

class AppServiceTest {

    private FeedbackService feedbackService;
    private GoogleDocsService googleDocsService;
    private OpenAiService openAiService;
    private TrelloService trelloService;
    private CacheService cacheService;
    private TelegramMessageHandler handler;
    private AppService appService;

    @BeforeEach
    void setUp() {
        feedbackService = mock(FeedbackService.class);
        googleDocsService = mock(GoogleDocsService.class);
        openAiService = mock(OpenAiService.class);
        trelloService = mock(TrelloService.class);
        cacheService = mock(CacheService.class);
        handler = mock(TelegramMessageHandler.class);
        appService = new AppService(feedbackService, googleDocsService, openAiService, trelloService, cacheService, handler);
    }

    @Test
    void handleFeedback_shouldCallDependentServices() throws Exception {
        BotUser user = new BotUser(1L, "Engineer", "Kyiv");
        FeedbackAnalysis analysis = new FeedbackAnalysis("Bug", 2, "Fix it");
        UpsertFeedbackDto upsertDto = new UpsertFeedbackDto();
        FeedbackDto dto = new FeedbackDto();

        when(openAiService.analyzeFeedback("text")).thenReturn(analysis);
        when(feedbackService.createFeedbackDto("text", user, analysis)).thenReturn(upsertDto);
        when(feedbackService.save(upsertDto)).thenReturn(dto);

        FeedbackDto result = appService.handleFeedback("text", user);

        assertEquals(dto, result, "Returned FeedbackDto should match the saved object");
        verify(openAiService).analyzeFeedback("text");
        verify(feedbackService).createFeedbackDto("text", user, analysis);
        verify(feedbackService).save(upsertDto);
        verify(googleDocsService).appendFeedback(dto);
        verifyNoInteractions(trelloService);
    }

    @Test
    void getTextFromUpdate_shouldReturnTrimmedText() {
        Update update = new Update();
        Message message = new Message();
        message.setText("  hello  ");
        update.setMessage(message);

        String text = appService.getTextFromUpdate(update);

        assertEquals("hello", text, "Returned text should be trimmed of spaces");
    }

    @Test
    void prepareMessage_shouldHandleStartCommand() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(1L);
        when(message.getText()).thenReturn("/start");

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId("1");
        expectedMessage.setText("Welcome!"); // StaticStrings.WELCOME
        when(handler.handleStartCommand(1L)).thenReturn(expectedMessage);

        SendMessage actual = appService.prepareMessage(update, "/start");

        verify(cacheService).setChatId(1L);
        verify(handler).handleStartCommand(1L);

        assertEquals(expectedMessage, actual, "Returned SendMessage object should match expected object");
        assertEquals("Welcome!", actual.getText(), "Message text should match StaticStrings.WELCOME");
        assertEquals("1", actual.getChatId(), "ChatId should match the Update message chatId");
    }
}
