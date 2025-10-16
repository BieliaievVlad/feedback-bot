package com.bieliaiev.feedback_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.enums.Branches;
import com.bieliaiev.feedback_bot.enums.Positions;
import com.bieliaiev.feedback_bot.handler.TelegramMessageHandler;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.utils.StaticStrings;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppService {

	private FeedbackService feedbackService;
	private GoogleDocsService googleDocsService;
	private OpenAiService openAiService;
	private TrelloService trelloService;
	private CacheService cacheService;
	private TelegramMessageHandler handler;
	
	public FeedbackDto handleFeedback(String text, BotUser user) throws Exception {
		
		FeedbackAnalysis analysis = openAiService.analyzeFeedback(text);
		UpsertFeedbackDto upsertDto = feedbackService.createFeedbackDto(text, user, analysis);
		FeedbackDto feedbackDto = feedbackService.save(upsertDto);
		googleDocsService.appendFeedback(feedbackDto);
		
		if (analysis.getLevel() >=4) {
			trelloService.createCard(StaticStrings.CARD_TITLE + feedbackDto.getAnalysis().getLevel(), feedbackDto.getFeedbackText());
		}
		
		return feedbackDto;
	}
	
	public String getTextFromUpdate (Update update) {
		
		if (!update.hasMessage()) {
			return null;
		}

		Message message = update.getMessage();

		String text = message.hasText() ? message.getText().trim() : null;

		if (text == null) {
			return null;
		}
		return text;
	}
	
	public SendMessage prepareMessage (Update update, String text) {
		
		SendMessage message = new SendMessage();
		
	    if (update == null || update.getMessage() == null) {
	        return null;
	    }
	    
		long chatId = update.getMessage().getChatId();
		BotUser user = cacheService.getUser(chatId);

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

		if (cacheService.isUserValid(user) && !text.startsWith("/")) {
			try {
				handleFeedback(text, user);
				return handler.handleFeedback(text, user);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		} else {
			return handler.handleInvalidUser(chatId);
		}
		
		return message;
	}
}
