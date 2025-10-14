package com.bieliaiev.feedback_bot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bieliaiev.feedback_bot.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {

	@Query("SELECT DISTINCT f.position FROM Feedback f")
	List<String> findDistinctPositions();

	@Query("SELECT DISTINCT f.branch FROM Feedback f")
	List<String> findDistinctBranches();

	@Query("SELECT DISTINCT f.level FROM Feedback f")
	List<String> findDistinctLevels();

	@Query("SELECT DISTINCT f.category FROM Feedback f")
	List<String> findDistinctCategories();
}
