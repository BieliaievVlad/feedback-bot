package com.bieliaiev.feedback_bot.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.mapper.FeedbackMapper;
import com.bieliaiev.feedback_bot.model.User;
import com.bieliaiev.feedback_bot.repository.FeedbackRepository;

@Service
public class FeedbackService {
	
	private FeedbackRepository repository;
	private FeedbackMapper mapper;

	public FeedbackService(FeedbackRepository repository, FeedbackMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public FeedbackDto save (UpsertFeedbackDto dto) {
		
		Feedback feedback = repository.save(new Feedback(
				dto.getCreatedAt(),
				dto.getUser().getChatId(),
				dto.getUser().getPosition(),
				dto.getUser().getBranch(),
				dto.getFeedbackText(),
				dto.getCategory(),
				dto.getLevel(),
				dto.getSolution()));
		
		return mapper.feedbackToDto(feedback);
	}
	
	public UpsertFeedbackDto createFeedbackDto(String text, User user) {
		return new UpsertFeedbackDto(LocalDateTime.now(), user, text, null, null, null);
	}
	
	public FeedbackDto saveFeedback (String text, User user) {
		return save(createFeedbackDto(text, user));
	}
}
