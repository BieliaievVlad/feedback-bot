package com.bieliaiev.feedback_bot.service;

import java.io.IOException;
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
	
	public FeedbackDto save (UpsertFeedbackDto dto) throws IOException {
		
		Feedback feedback = Feedback.builder()
				.createdAt(dto.getCreatedAt())
				.chatId(dto.getUser().getChatId())
				.position(dto.getUser().getPosition())
				.branch(dto.getUser().getBranch())
				.feedbackText(dto.getFeedbackText())
				.category(dto.getCategory())
				.level(dto.getLevel())
				.solution(dto.getSolution())
				.build();
		
		repository.save(feedback);
		
		return mapper.feedbackToDto(feedback);
	}
	
	public UpsertFeedbackDto createFeedbackDto(String text, User user, FeedbackAnalysis analysis) throws Exception {
		return new UpsertFeedbackDto(
				LocalDateTime.now(), 
				user, 
				text, 
				analysis.getCategory(), 
				analysis.getLevel(), 
				analysis.getSolution());
	}
	
	public FeedbackDto saveFeedback (String text, User user, FeedbackAnalysis analysis) throws Exception {
		return save(createFeedbackDto(text, user, analysis));
	}
}
