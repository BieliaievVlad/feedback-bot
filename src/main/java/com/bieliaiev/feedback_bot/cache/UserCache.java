package com.bieliaiev.feedback_bot.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.bieliaiev.feedback_bot.model.BotUser;

@Component
public class UserCache {

	private Map<Long, BotUser> users = new ConcurrentHashMap<>();
	
	public void putUser (BotUser user) {
		users.put(user.getChatId(), user);
	}
	
	public BotUser getUser (Long chatId) {
		return users.get(chatId);
	}
}
