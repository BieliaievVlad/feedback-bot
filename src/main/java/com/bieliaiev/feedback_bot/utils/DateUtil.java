package com.bieliaiev.feedback_bot.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtil {

	public List<LocalDate> getDateListOfInterval(LocalDate start, LocalDate end) {

		List<LocalDate> dateList = new ArrayList<>();
		LocalDate currentDate = start;
		while (!currentDate.isAfter(end)) {
			dateList.add(currentDate);
			currentDate = currentDate.plusDays(1);
		}
		return dateList;
	}
}
