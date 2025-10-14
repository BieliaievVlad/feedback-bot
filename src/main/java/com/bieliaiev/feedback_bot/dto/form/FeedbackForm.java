package com.bieliaiev.feedback_bot.dto.form;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackForm {

	private List<String> positions = Collections.emptyList();
	private List<String> branches = Collections.emptyList();
	private List<String> categories = Collections.emptyList();
	private List<String> levels = Collections.emptyList();
}
