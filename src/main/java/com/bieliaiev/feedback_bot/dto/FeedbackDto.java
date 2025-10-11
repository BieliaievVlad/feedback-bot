package com.bieliaiev.feedback_bot.dto;

import java.time.LocalDateTime;

import com.bieliaiev.feedback_bot.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackDto {

	private Long id;
	private LocalDateTime createdAt;
	private User user;
	private String feedbackText;
	private String category;
	private Integer level;
	private String solution;
}
