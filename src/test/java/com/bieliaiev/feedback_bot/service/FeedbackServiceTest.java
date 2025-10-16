package com.bieliaiev.feedback_bot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.mapper.FeedbackMapper;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.repository.FeedbackRepository;

class FeedbackServiceTest {

    private FeedbackRepository repository;
    private FeedbackMapper mapper;
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        repository = mock(FeedbackRepository.class);
        mapper = mock(FeedbackMapper.class);
        feedbackService = new FeedbackService(repository, mapper);
    }

    @Test
    void save_shouldPersistFeedbackAndReturnDto() throws Exception {
        BotUser user = new BotUser(1L, "Engineer", "Kyiv");
        UpsertFeedbackDto upsertDto = new UpsertFeedbackDto(
                LocalDateTime.now(),
                user,
                "Test feedback",
                "Bug",
                2,
                "Fix it"
        );

        FeedbackDto feedbackDto = new FeedbackDto();

        when(mapper.feedbackToDto(any(Feedback.class))).thenReturn(feedbackDto);

        FeedbackDto result = feedbackService.save(upsertDto);

        assertEquals(feedbackDto, result, "Returned FeedbackDto should match the mapper's result");
        verify(repository).save(any(Feedback.class));
        verify(mapper).feedbackToDto(any(Feedback.class));
    }
}