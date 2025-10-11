package com.bieliaiev.feedback_bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

	private Long chatId;
	private String position;
	private String branch;
}
