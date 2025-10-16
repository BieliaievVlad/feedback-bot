package com.bieliaiev.feedback_bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAnalysis {

	private String category;
	private Integer level;
	private String solution;
}
