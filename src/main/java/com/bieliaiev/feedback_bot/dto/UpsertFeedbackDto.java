package com.bieliaiev.feedback_bot.dto;

import java.time.LocalDateTime;

import com.bieliaiev.feedback_bot.model.BotUser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpsertFeedbackDto {

	private LocalDateTime createdAt;
	private BotUser user;
	private String feedbackText;
	private String category;
	private Integer level;
	private String solution;
}
