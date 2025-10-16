package com.bieliaiev.feedback_bot.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bieliaiev.feedback_bot.dto.FeedbackDto;
import com.bieliaiev.feedback_bot.dto.UpsertFeedbackDto;
import com.bieliaiev.feedback_bot.dto.form.FeedbackForm;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.mapper.FeedbackMapper;
import com.bieliaiev.feedback_bot.model.FeedbackAnalysis;
import com.bieliaiev.feedback_bot.model.BotUser;
import com.bieliaiev.feedback_bot.repository.FeedbackRepository;
import com.bieliaiev.feedback_bot.specification.FeedbackSpecification;
import com.bieliaiev.feedback_bot.utils.DateUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeedbackService {

	private FeedbackRepository repository;
	private FeedbackMapper mapper;

	public FeedbackDto save(UpsertFeedbackDto dto) throws IOException {

		Feedback feedback = Feedback.builder()
												   .createdAt(dto.getCreatedAt())
												   .chatId(dto.getUser().getChatId())
												   .position(dto.getUser().getPosition())
												   .branch(dto.getUser().getBranch())
												   .feedbackText(dto.getFeedbackText())
												   .category(dto.getCategory())
												   .level(dto.getLevel())
												   .solution(dto.getSolution()).build();
		repository.save(feedback);

		return mapper.feedbackToDto(feedback);
	}

	public UpsertFeedbackDto createFeedbackDto(String text, BotUser user, FeedbackAnalysis analysis) throws Exception {
		return new UpsertFeedbackDto(LocalDateTime.now(), 
													user, 
													text, 
													analysis.getCategory(), 
													analysis.getLevel(),
													analysis.getSolution());
	}

	public FeedbackDto saveFeedback(String text, BotUser user, FeedbackAnalysis analysis) throws Exception {
		return save(createFeedbackDto(text, 
													user, 
													analysis));
	}

	public List<Feedback> filterFeedbacks(String startDate, 
														 String endDate, 
														 String position, 
														 String branch,
														 String category,
														 Long level) {
		LocalDate start = null;
		LocalDate end = null;
		List<Feedback> feedbackList = new ArrayList<>();

		if (position == null || position.isEmpty()) {
			position = null;
		}

		if (branch == null || branch.isEmpty()) {
			branch = null;
		}

		if (category == null || category.isEmpty()) {
			category = null;
		}

		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			start = LocalDate.parse(startDate);
			end = LocalDate.parse(endDate);

			List<LocalDate> dates = DateUtil.getDateListOfInterval(start, end);

			for (LocalDate date : dates) {
				Specification<Feedback> specification = Specification.where(FeedbackSpecification.filterByDate(date))
																						  .and(FeedbackSpecification.filterByPosition(position))
																						  .and(FeedbackSpecification.filterByBranch(branch))
																						  .and(FeedbackSpecification.filterByCategory(category))
																						  .and(FeedbackSpecification.filterByLevel(level));
				List<Feedback> logs = repository.findAll(specification);
				feedbackList.addAll(logs);
			}
			
			return feedbackList.stream()
					.sorted(Comparator.comparing(Feedback::getId))
					.toList();

		} else {
			LocalDate date = null;
			Specification<Feedback> specification = Specification.where(FeedbackSpecification.filterByDate(date))
																					  .and(FeedbackSpecification.filterByPosition(position))
																					  .and(FeedbackSpecification.filterByBranch(branch))
																					  .and(FeedbackSpecification.filterByCategory(category))
																					  .and(FeedbackSpecification.filterByLevel(level));			
			return repository.findAll(specification).stream()
					.sorted(Comparator.comparing(Feedback::getId))
					.toList();
		}
	}
	
	public FeedbackForm prepareFeedbackForm() {
		FeedbackForm form = new FeedbackForm();
		
		form.setPositions(findPositions());
		form.setBranches(findBranches());
		form.setCategories(findCategories());
		form.setLevels(findLevels());
		
		return form;
	}
	
	private List<String> findPositions () {
		return repository.findDistinctPositions();
	}
	
	private List<String> findBranches () {
		return repository.findDistinctBranches();
	}
	
	private List<String> findCategories () {
		return repository.findDistinctCategories();
	}
	
	private List<String> findLevels () {
		return repository.findDistinctLevels();
	}
}
