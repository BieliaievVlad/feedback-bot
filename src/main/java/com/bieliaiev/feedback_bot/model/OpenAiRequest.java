package com.bieliaiev.feedback_bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenAiRequest {

	private String model;
	private Message[] messages;
	private double temperature;
}
