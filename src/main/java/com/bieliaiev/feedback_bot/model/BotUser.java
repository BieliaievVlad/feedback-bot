package com.bieliaiev.feedback_bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotUser {

	private Long chatId;
	private String position;
	private String branch;
}
