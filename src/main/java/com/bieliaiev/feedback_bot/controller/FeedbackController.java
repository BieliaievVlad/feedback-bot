package com.bieliaiev.feedback_bot.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bieliaiev.feedback_bot.dto.form.FeedbackForm;
import com.bieliaiev.feedback_bot.entity.Feedback;
import com.bieliaiev.feedback_bot.service.FeedbackService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FeedbackController {
	
	private FeedbackService service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/feedbacks")
	public String showLogsForm(@RequestParam(value = "startDate", required = false) String startDate,
							   			   @RequestParam(value = "endDate", required = false) String endDate,
							   			   @RequestParam(value = "positionName", required = false) String position,
							   			   @RequestParam(value = "branchName", required = false) String branch, 
							   			   @RequestParam(value = "categoryName", required = false) String category, 
							   			   @RequestParam(value = "levelValue", required = false) Long level, 
							   			   Model model) {
		
	    if ((startDate == null ||startDate.isEmpty()) || (endDate == null || endDate.isEmpty())) {
            model.addAttribute("dateAlert", "Please choose both dates or leave it empty to list all feedbacks");
        }
		
		List<Feedback> feedbacks = service.filterFeedbacks(startDate, endDate, position, branch, category, level);
		FeedbackForm form = service.prepareFeedbackForm();

		model.addAttribute("feedbacks", feedbacks);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("position", position);
		model.addAttribute("branch", branch);
		model.addAttribute("category", category);
		model.addAttribute("level", level);
		model.addAttribute("positionNames", form.getPositions());
		model.addAttribute("branchNames", form.getBranches());
		model.addAttribute("categoryNames", form.getCategories());
		model.addAttribute("levels", form.getLevels());
		
		return "feedbacks";
	}
}
