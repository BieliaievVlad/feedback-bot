package com.bieliaiev.feedback_bot.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class DateUtilTest {

    @Test
    void shouldReturnAllDatesWithinInterval() {
        LocalDate start = LocalDate.of(2025, 10, 1);
        LocalDate end = LocalDate.of(2025, 10, 3);

        List<LocalDate> result = DateUtil.getDateListOfInterval(start, end);

        assertEquals(3, result.size(), "List should include all 3 days");
        assertEquals(start, result.get(0), "First date should match start date");
        assertEquals(end, result.get(result.size() - 1), "Last date should match end date");
        assertTrue(result.contains(LocalDate.of(2025, 10, 2)), "List should contain the middle date");
    }

    @Test
    void shouldReturnSingleDateWhenStartEqualsEnd() {
        LocalDate date = LocalDate.of(2025, 10, 15);

        List<LocalDate> result = DateUtil.getDateListOfInterval(date, date);

        assertEquals(1, result.size(), "List should contain one date when start equals end");
        assertEquals(date, result.get(0), "The only date should match start/end date");
    }

    @Test
    void shouldReturnEmptyListWhenStartAfterEnd() {
        LocalDate start = LocalDate.of(2025, 10, 10);
        LocalDate end = LocalDate.of(2025, 10, 5);

        List<LocalDate> result = DateUtil.getDateListOfInterval(start, end);

        assertTrue(result.isEmpty(), "List should be empty when start date is after end date");
    }

    @Test
    void shouldHandleYearBoundaryCorrectly() {
        LocalDate start = LocalDate.of(2024, 12, 30);
        LocalDate end = LocalDate.of(2025, 1, 2);

        List<LocalDate> result = DateUtil.getDateListOfInterval(start, end);

        assertEquals(4, result.size(), "List should include all days across year boundary");
        assertEquals(LocalDate.of(2024, 12, 30), result.get(0));
        assertEquals(LocalDate.of(2025, 1, 2), result.get(3));
    }
}