package com.bieliaiev.feedback_bot.dto;

import java.time.LocalDateTime;

import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.BotUser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackDto {

	private Long id;
	private LocalDateTime createdAt;
	private BotUser user;
	private String feedbackText;
	private FeedbackAnalysis analysis;
}
