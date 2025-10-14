package com.bieliaiev.feedback_bot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedback", schema = "bot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "chat_id")
	private Long chatId;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "branch")
	private String branch;
	
	@Column(name = "feedback_text")
	private String feedbackText;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "solution")
	private String solution;
	
}
