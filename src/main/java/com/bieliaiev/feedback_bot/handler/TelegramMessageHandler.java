package com.bieliaiev.feedback_bot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.bieliaiev.feedback_bot.keyboard.KeyboardFactory;
import com.bieliaiev.feedback_bot.model.User;
import com.bieliaiev.feedback_bot.service.FeedbackService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TelegramMessageHandler {
	
	private FeedbackService feedbackService;

	public SendMessage handleStartCommand (Long chatId) {
		SendMessage response = new SendMessage(String.valueOf(chatId), "Welcome to Feedback Bot! Please select your position:");
	    response.setReplyMarkup(KeyboardFactory.createPositionsKeyboard());
	    return response;
	}
	
	public SendMessage handlePositionResponse (Long chatId) {
		SendMessage response = new SendMessage(String.valueOf(chatId), "Please select your branch:");
		response.setReplyMarkup(KeyboardFactory.createBranchesKeyboard());
		return response;
	}
	
	public SendMessage handleBranchResponse (Long chatId) {
		SendMessage response = new SendMessage(String.valueOf(chatId), "Thanks, now you can leave your feedback!");
//		response.setReplyMarkup(KeyboardFactory.createFeedbackKeyboard());
		return response;
	}
	
	public SendMessage handleFeedback (String text, User user) throws Exception {
		feedbackService.saveFeedback(text, user);
		return new SendMessage(String.valueOf(user.getChatId()), "Thank you for your feedback!");
	}
	
//	public SendMessage askForFeedback (Long chatId) {
//		return new SendMessage(String.valueOf(chatId), "Please type your feedback below and send.");
//	}
	
	public SendMessage handleInvalidUser (Long chatId) {
		return new SendMessage(String.valueOf(chatId), "Please restart the bot first by sending /start");
	}
}
