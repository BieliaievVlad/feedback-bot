package com.bieliaiev.feedback_bot.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.bieliaiev.feedback_bot.model.User;

@Component
public class UserCache {

	private Map<Long, User> users = new ConcurrentHashMap<>();
	
	public void putUser (User user) {
		users.put(user.getChatId(), user);
	}
	
	public User getUser (Long chatId) {
		return users.get(chatId);
	}
}
