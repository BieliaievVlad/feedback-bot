package com.bieliaiev.feedback_bot.service;

import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.cache.UserCache;
import com.bieliaiev.feedback_bot.model.BotUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CacheService {

	private UserCache cache;
	
	public void setChatId (Long chatId) {
		
		BotUser user = cache.getUser(chatId);
		
		if (user == null) {
			cache.putUser(new BotUser(chatId, null, null));
		}
	}
	
	public void setPosition (Long chatId, String position) {
		
		BotUser user = cache.getUser(chatId);
		
		if (user != null && user.getChatId() != null) {
			user.setPosition(position);
			cache.putUser(user);
		}
	}
	
	public void setBranch (Long chatId, String branch) {
		
		BotUser user = cache.getUser(chatId);
		
		if (user != null && user.getChatId() != null && user.getPosition() != null) {
			user.setBranch(branch);
			cache.putUser(user);
		}
	}
	
	public BotUser getUser(Long chatId) {
		return cache.getUser(chatId);
	}
	
	public boolean isUserValid (BotUser user) {
		return user != null && 
				user.getChatId() != null &&
				user.getPosition() != null &&
				user.getBranch() !=null;
	}
}
