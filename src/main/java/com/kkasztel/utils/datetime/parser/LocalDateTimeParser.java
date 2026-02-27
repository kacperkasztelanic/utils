package com.kkasztel.utils.datetime.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Parses date, time, and date-time strings by auto-detecting their format pattern.
 * Supports day-month-year (DMY) and month-day-year (MDY) conventions.
 */
@RequiredArgsConstructor(access = PRIVATE)
public class LocalDateTimeParser {

    private final LocalDateTimePatternRecognizer recognizer;

    public static LocalDateTimeParser dmy() {
        return new LocalDateTimeParser(LocalDateTimePatternRecognizer.dmy());
    }

    public static LocalDateTimeParser mdy() {
        return new LocalDateTimeParser(LocalDateTimePatternRecognizer.mdy());
    }

    public Optional<LocalTime> parseTime(final String str) {
        return recognizer.recognizeTimePattern(str)
                .map(DateTimeFormatter::ofPattern)
                .map(f -> LocalTime.parse(str, f));
    }

    public Optional<LocalDate> parseDate(final String str) {
        return recognizer.recognizeDatePattern(str)
                .map(DateTimeFormatter::ofPattern)
                .map(f -> LocalDate.parse(str, f));
    }

    public Optional<LocalDateTime> parseDateTime(final String str) {
        return recognizer.recognizeDateTimePattern(str)
                .map(DateTimeFormatter::ofPattern)
                .map(f -> LocalDateTime.parse(str, f));
    }
}
