package com.bieliaiev.feedback_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.bieliaiev.feedback_bot.config.BotConfig;
import com.bieliaiev.feedback_bot.enums.Branches;
import com.bieliaiev.feedback_bot.enums.Positions;
import com.bieliaiev.feedback_bot.handler.TelegramMessageHandler;
import com.bieliaiev.feedback_bot.model.User;
import com.bieliaiev.feedback_bot.service.CacheService;

@Component
public class FeedbackBot extends TelegramWebhookBot {

	private final BotConfig config;
	private final TelegramMessageHandler handler;
	private final CacheService cacheService;

	public FeedbackBot(BotConfig config, TelegramMessageHandler handler, CacheService cacheService) {
		super(config.getBotPath());
		this.config = config;
		this.handler = handler;
		this.cacheService = cacheService;
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

		if (!update.hasMessage()) {
			return null;
		}

		Message message = update.getMessage();
		long chatId = message.getChatId();

		String text = message.hasText() ? message.getText().trim() : null;

		if (text == null) {
			return null;
		}

		User user = cacheService.getUser(chatId);

		if (text.equalsIgnoreCase("/start")) {
			cacheService.setChatId(chatId);
			return handler.handleStartCommand(chatId);
		}

		for (Positions position : Positions.values()) {
			if (text.equalsIgnoreCase(position.getText())) {
				cacheService.setPosition(chatId, text);
				return handler.handlePositionResponse(chatId);
			}
		}

		for (Branches branch : Branches.values()) {
			if (text.equalsIgnoreCase(branch.getText())) {
				cacheService.setBranch(chatId, text);
				return handler.handleBranchResponse(chatId);
			}
		}

		if (text.equalsIgnoreCase("send feedback")) {
			if (cacheService.isUserValid(user)) {
				return handler.askForFeedback(chatId);

			} else {
				return handler.handleInvalidUser(chatId);
			}
		}

		if (cacheService.isUserValid(user) && !text.startsWith("/")) {
			return handler.handleFeedback(text, user);
		}

		return null;
	}
}
