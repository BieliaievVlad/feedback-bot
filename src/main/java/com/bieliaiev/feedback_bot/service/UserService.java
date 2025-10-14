package com.bieliaiev.feedback_bot.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.entity.User;
import com.bieliaiev.feedback_bot.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

}
