package com.bieliaiev.feedback_bot.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;

class FeedbackMapperTest {

    private FeedbackMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FeedbackMapper();
    }

    @Test
    void feedbackToDto_shouldMapAllFieldsCorrectly() {

        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setCreatedAt(LocalDateTime.of(2025, 10, 15, 10, 0));
        feedback.setChatId(123456789L);
        feedback.setPosition("Electrician");
        feedback.setBranch("Kyiv");
        feedback.setFeedbackText("Everything works fine");
        feedback.setCategory("Positive");
        feedback.setLevel(1);
        feedback.setSolution("No action needed");

        FeedbackDto dto = mapper.feedbackToDto(feedback);

        assertNotNull(dto, "DTO should not be null");
        assertEquals(1L, dto.getId(), "ID should be mapped");
        assertEquals(LocalDateTime.of(2025, 10, 15, 10, 0), dto.getCreatedAt(), "CreatedAt should be mapped");

        BotUser user = dto.getBotUser();
        assertNotNull(user, "User should not be null");
        assertEquals(123456789L, user.getChatId(), "ChatId should be mapped");
        assertEquals("Electrician", user.getPosition(), "Position should be mapped");
        assertEquals("Kyiv", user.getBranch(), "Branch should be mapped");

        assertEquals("Everything works fine", dto.getFeedbackText(), "Feedback text should be mapped");

        FeedbackAnalysis analysis = dto.getAnalysis();
        assertNotNull(analysis, "Analysis should not be null");
        assertEquals("Positive", analysis.getCategory(), "Category should be mapped");
        assertEquals(1, analysis.getLevel(), "Level should be mapped");
        assertEquals("No action needed", analysis.getSolution(), "Solution should be mapped");
    }
}
