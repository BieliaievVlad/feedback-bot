package com.bieliaiev.feedback_bot.service;

import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.cache.UserCache;
import com.bieliaiev.feedback_bot.model.User;

@Service
public class CacheService {

	private UserCache cache;
	
	public CacheService (UserCache cache) {
		this.cache = cache;
	}
	
	public void setChatId (Long chatId) {
		
		User user = cache.getUser(chatId);
		
		if (user == null) {
			cache.putUser(new User(chatId, null, null));
		}
	}
	
	public void setPosition (Long chatId, String position) {
		
		User user = cache.getUser(chatId);
		
		if (user != null && user.getChatId() != null) {
			user.setPosition(position);
			cache.putUser(user);
		}
	}
	
	public void setBranch (Long chatId, String branch) {
		
		User user = cache.getUser(chatId);
		
		if (user != null && user.getChatId() != null && user.getPosition() != null) {
			user.setBranch(branch);
			cache.putUser(user);
		}
	}
	
	public User getUser(Long chatId) {
		return cache.getUser(chatId);
	}
	
	public boolean isUserValid (User user) {
		return user != null && 
				user.getChatId() != null &&
				user.getPosition() != null &&
				user.getBranch() !=null;
	}
}
