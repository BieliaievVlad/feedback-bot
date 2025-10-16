package com.bieliaiev.feedback_bot.dto;

import java.time.LocalDateTime;

import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.BotUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

	private Long id;
	private LocalDateTime createdAt;
	private BotUser botUser;
	private String feedbackText;
	private FeedbackAnalysis analysis;
}
