package com.apexon.stock_analysis.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CalcDateUtil {


    public static LocalDate getPreviousWorkingDay(LocalDate inputDate) {
        // Define BSE holidays
        Set<LocalDate> bseHolidays = new HashSet<>(Arrays.asList(
                LocalDate.of(2025, 1, 26), // Republic Day
                LocalDate.of(2025, 3, 17), // Holi
                LocalDate.of(2025, 4, 14), // Dr. Ambedkar Jayanti
                LocalDate.of(2025, 5, 1),  // Maharashtra Day
                LocalDate.of(2025, 8, 15), // Independence Day
                LocalDate.of(2025, 10, 2), // Gandhi Jayanti
                LocalDate.of(2025, 11, 1)  // Diwali Balipratipada
        ));

        LocalDate date = inputDate.minusDays(1);
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                bseHolidays.contains(date)) {
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                date = date.minusDays(2); // Subtract two days for Sunday
            } else {
                date = date.minusDays(1); // Subtract one day for Saturday or holiday
            }
        }
        return date;
    }
}
