package com.bieliaiev.feedback_bot.mapper;

import org.springframework.stereotype.Component;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.BotUser;

@Component
public class FeedbackMapper {

	public FeedbackDto feedbackToDto (Feedback feedback) {
		return new FeedbackDto (
				feedback.getId(),
				feedback.getCreatedAt(),
				new BotUser (
						feedback.getChatId(),
						feedback.getPosition(),
						feedback.getBranch()
						),
				feedback.getFeedbackText(),
				new FeedbackAnalysis(
						feedback.getCategory(),
						feedback.getLevel(),
						feedback.getSolution()
						));
	}
}
