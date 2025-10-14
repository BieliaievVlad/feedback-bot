package com.bieliaiev.feedback_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bieliaiev.feedback_bot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
