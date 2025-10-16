package com.bieliaiev.feedback_bot.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.bieliaiev.feedback_bot.entity.Feedback;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FeedbackSpecification {

	public static Specification<Feedback> filterByDate(LocalDate date) {
		return (root, query, criteriaBuilder) -> {
			if (date != null) {
				return criteriaBuilder.equal(criteriaBuilder.function("DATE", LocalDate.class, root.get("createdAt")),
						date);
			}
			return criteriaBuilder.conjunction();
		};
	}

	public static Specification<Feedback> filterByPosition(String position) {
		return (root, query, criteriaBuilder) -> {
			if (position != null) {
				return criteriaBuilder.equal(root.get("position"), position);
			}
			return criteriaBuilder.conjunction();
		};
	}

	public static Specification<Feedback> filterByBranch(String branch) {
		return (root, query, criteriaBuilder) -> {
			if (branch != null) {
				return criteriaBuilder.equal(root.get("branch"), branch);
			}
			return criteriaBuilder.conjunction();
		};
	}

	public static Specification<Feedback> filterByCategory(String category) {
		return (root, query, criteriaBuilder) -> {
			if (category != null) {
				return criteriaBuilder.equal(root.get("category"), category);
			}
			return criteriaBuilder.conjunction();
		};
	}

	public static Specification<Feedback> filterByLevel(Long level) {
		return (root, query, criteriaBuilder) -> {
			if (level != null) {
				return criteriaBuilder.equal(root.get("level"), level);
			}
			return criteriaBuilder.conjunction();
		};
	}
}
