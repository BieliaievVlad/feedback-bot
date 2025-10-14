package com.bieliaiev.feedback_bot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.bieliaiev.feedback_bot.keyboard.KeyboardFactory;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.utils.StaticStrings;

@Service
public class TelegramMessageHandler {

	public SendMessage handleStartCommand (Long chatId) {
		SendMessage response = new SendMessage(String.valueOf(chatId), StaticStrings.WELCOME);
	    response.setReplyMarkup(KeyboardFactory.createPositionsKeyboard());
	    return response;
	}
	
	public SendMessage handlePositionResponse (Long chatId) {
		SendMessage response = new SendMessage(String.valueOf(chatId), StaticStrings.SELECT_BRANCH);
		response.setReplyMarkup(KeyboardFactory.createBranchesKeyboard());
		return response;
	}
	
	public SendMessage handleBranchResponse (Long chatId) {
		return new SendMessage(String.valueOf(chatId), StaticStrings.USER_REGISTRATION_DONE);
	}
	
	public SendMessage handleFeedback (String text, BotUser user) {
		return new SendMessage(String.valueOf(user.getChatId()), StaticStrings.FEEDBACK_SAVED);
	}
	
	public SendMessage handleInvalidUser (Long chatId) {
		return new SendMessage(String.valueOf(chatId), StaticStrings.RESTART);
	}
}
