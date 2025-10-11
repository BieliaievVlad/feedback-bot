package com.bieliaiev.feedback_bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.bieliaiev.feedback_bot.bot.FeedbackBot;

@RestController
@RequestMapping("${telegram.webhook-path}")
public class WebhookController {

	private final FeedbackBot bot;
	
	public WebhookController(FeedbackBot bot) {
		this.bot = bot;
	}
	
	@PostMapping
	public ResponseEntity<?> onUpdateReceived(@RequestBody Update update) {
	    BotApiMethod<?> response = bot.onWebhookUpdateReceived(update);
	    return ResponseEntity.ok(response);
	}
}
