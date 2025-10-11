package com.bieliaiev.feedback_bot.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.mapper.FeedbackMapper;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.User;
import com.bieliaiev.feedback_bot.repository.FeedbackRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackService {
	
	private FeedbackRepository repository;
	private FeedbackMapper mapper;
	private OpenAiFeedbackService service;
	
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
	
	public UpsertFeedbackDto createFeedbackDto(String text, User user) throws Exception {
		FeedbackAnalysis analysis = service.analyzeFeedback(text);
		return new UpsertFeedbackDto(
				LocalDateTime.now(), 
				user, 
				text, 
				analysis.getCategory(), 
				analysis.getLevel(), 
				analysis.getSolution());
	}
	
	public FeedbackDto saveFeedback (String text, User user) throws Exception {
		return save(createFeedbackDto(text, user));
	}
}
