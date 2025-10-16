package com.bieliaiev.feedback_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.bieliaiev.feedback_bot.config.BotConfig;
import com.bieliaiev.feedback_bot.service.AppService;

@Component
public class FeedbackBot extends TelegramWebhookBot {

	private final BotConfig config;
	private final AppService service;

	public FeedbackBot(BotConfig config, AppService service) {
		super(config.getBotPath());
		this.config = config;
		this.service = service;
	}

	@Override
	public String getBotUsername() {
		return config.getBotUsername();
	}

	@Override
	public String getBotPath() {
		return config.getBotPath();
	}

	@Override
	public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

		String text = service.getTextFromUpdate(update);

		return service.prepareMessage(update, text);
	}
}
