package com.bieliaiev.feedback_bot.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import com.bieliaiev.feedback_bot.keyboard.KeyboardFactory;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.utils.StaticStrings;

class TelegramMessageHandlerTest {

    private TelegramMessageHandler handler;

    @BeforeEach
    void setUp() {
        handler = new TelegramMessageHandler();
    }

    @Test
    void handleStartCommand_shouldReturnWelcomeMessage() {
        try (MockedStatic<KeyboardFactory> mockedKeyboardFactory = mockStatic(KeyboardFactory.class)) {
            ReplyKeyboardMarkup mockKeyboard = mock(ReplyKeyboardMarkup.class);
            mockedKeyboardFactory.when(KeyboardFactory::createPositionsKeyboard).thenReturn(mockKeyboard);

            SendMessage message = handler.handleStartCommand(123L);

            assertEquals("123", message.getChatId());
            assertEquals(StaticStrings.WELCOME, message.getText());
            assertEquals(mockKeyboard, message.getReplyMarkup());
        }
    }

    @Test
    void handlePositionResponse_shouldReturnBranchSelectionMessage() {
        try (MockedStatic<KeyboardFactory> mockedKeyboardFactory = mockStatic(KeyboardFactory.class)) {
            ReplyKeyboardMarkup mockKeyboard = mock(ReplyKeyboardMarkup.class);
            mockedKeyboardFactory.when(KeyboardFactory::createBranchesKeyboard).thenReturn(mockKeyboard);

            SendMessage message = handler.handlePositionResponse(321L);

            assertEquals("321", message.getChatId());
            assertEquals(StaticStrings.SELECT_BRANCH, message.getText());
            assertEquals(mockKeyboard, message.getReplyMarkup());
        }
    }

    @Test
    void handleBranchResponse_shouldReturnRegistrationDoneMessage() {
        SendMessage message = handler.handleBranchResponse(456L);

        assertEquals("456", message.getChatId());
        assertEquals(StaticStrings.USER_REGISTRATION_DONE, message.getText());
        assertNull(message.getReplyMarkup());
    }

    @Test
    void handleFeedback_shouldReturnFeedbackSavedMessage() {
        BotUser user = new BotUser();
        user.setChatId(789L);

        SendMessage message = handler.handleFeedback("some feedback", user);

        assertEquals("789", message.getChatId());
        assertEquals(StaticStrings.FEEDBACK_SAVED, message.getText());
    }

    @Test
    void handleInvalidUser_shouldReturnRestartMessage() {
        SendMessage message = handler.handleInvalidUser(111L);

        assertEquals("111", message.getChatId());
        assertEquals(StaticStrings.RESTART, message.getText());
    }
}
