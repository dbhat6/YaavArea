package com.yaavarea.server.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtils {

    // Calculate the difference in days between two dates
    public static long calculateDaysDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    // Calculate the difference in months between two dates
    public static long calculateMonthsDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    // Calculate the difference in years between two dates
    public static long calculateYearsDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.YEARS.between(startDate, endDate);
    }

    // Calculate the date after adding a specific number of days to a given date
    public static LocalDateTime addDays(LocalDateTime date, long daysToAdd) {
        return date.toLocalDate().plusDays(daysToAdd).atStartOfDay();
    }

    // Calculate the date after adding a specific number of months to a given date
    public static LocalDate addMonths(LocalDate date, long monthsToAdd) {
        return date.plusMonths(monthsToAdd);
    }

    // Calculate the date after adding a specific number of years to a given date
    public static LocalDate addYears(LocalDate date, long yearsToAdd) {
        return date.plusYears(yearsToAdd);
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
