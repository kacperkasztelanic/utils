package com.kkasztel.utils.datetime.parser;

import lombok.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.kkasztel.utils.Optionals.some;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LocalDateTimePatternRecognizerTest {

    private final LocalDateTimePatternRecognizer dmy = LocalDateTimePatternRecognizer.dmy();
    private final LocalDateTimePatternRecognizer mdy = LocalDateTimePatternRecognizer.mdy();

    @ParameterizedTest
    @MethodSource("provideDates")
    void recognizesDatePattern(final String input, final String expectedDmy, final String expectedMdy) {
        assertEquals(some(expectedDmy), dmy.recognizeDatePattern(input));
        assertEquals(some(expectedMdy), mdy.recognizeDatePattern(input));
    }

    @ParameterizedTest
    @MethodSource("provideTimes")
    void recognizesTimePattern(final String input, final String expectedDmy, final String expectedMdy) {
        assertEquals(some(expectedDmy), dmy.recognizeTimePattern(input));
        assertEquals(some(expectedMdy), mdy.recognizeTimePattern(input));
    }

    @ParameterizedTest
    @MethodSource("provideDateTimes")
    void recognizesDateTimePattern(final String input, final String expectedDmy, final String expectedMdy) {
        assertEquals(some(expectedDmy), dmy.recognizeDateTimePattern(input));
        assertEquals(some(expectedMdy), mdy.recognizeDateTimePattern(input));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideDates() {
        return provide(concat(dates(), dateTimes()));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideTimes() {
        return provide(concat(times(), dateTimes()));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideDateTimes() {
        return provide(dateTimes());
    }

    private static <T> List<T> concat(final List<T> a, final List<T> b) {
        return Stream.of(a, b)
                .flatMap(Collection::stream)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private static Stream<Arguments> provide(final List<Entry> entries) {
        return entries.stream().map(e -> arguments(e.getInput(), e.getDmyPattern(), e.getMdyPattern()));
    }

    private static List<Entry> dates() {
        return asList(
                Entry.of("20200101", "yyyyMMdd", "yyyyMMdd"),
                Entry.of("2020-01-01", "yyyy-MM-dd", "yyyy-dd-MM"),
                Entry.of("2020/01/01", "yyyy/MM/dd", "yyyy/dd/MM"),
                Entry.of("2020.01.01", "yyyy.MM.dd", "yyyy.dd.MM"),
                Entry.of("2020 01 01", "yyyy MM dd", "yyyy dd MM"),
                Entry.of("01-01-2020", "dd-MM-yyyy", "MM-dd-yyyy"),
                Entry.of("01/01/2020", "dd/MM/yyyy", "MM/dd/yyyy"),
                Entry.of("01.01.2020", "dd.MM.yyyy", "MM.dd.yyyy"),
                Entry.of("01 01 2020", "dd MM yyyy", "MM dd yyyy")
        );
    }

    private static List<Entry> times() {
        return asList(
                Entry.of("1112", "HHmm"),
                Entry.of("11:12", "HH:mm"),
                Entry.of("11-12", "HH-mm"),
                Entry.of("11/12", "HH/mm"),
                Entry.of("11.12", "HH.mm"),
                Entry.of("11 12", "HH mm"),
                Entry.of("111210", "HHmmss"),
                Entry.of("11:12:10", "HH:mm:ss"),
                Entry.of("11-12-10", "HH-mm-ss"),
                Entry.of("11/12/10", "HH/mm/ss"),
                Entry.of("11.12.10", "HH.mm.ss"),
                Entry.of("11 12 10", "HH mm ss"),
                Entry.of("11:12:10.123", "HH:mm:ss.SSS"),
                Entry.of("11-12-10.123", "HH-mm-ss.SSS"),
                Entry.of("11/12/10.123", "HH/mm/ss.SSS"),
                Entry.of("11.12.10.123", "HH.mm.ss.SSS"),
                Entry.of("11 12 10.123", "HH mm ss.SSS")
        );
    }

    private static List<String> separators() {
        return asList("", "_", " ", ".", "-", "/", "T");
    }

    private static List<Entry> dateTimes() {
        final List<Entry> dateEntries = dates();
        final List<Entry> timeEntries = times();
        final List<String> separators = separators();
        return dateEntries.stream()
                .flatMap(d -> timeEntries.stream()
                        .flatMap(t -> separators.stream()
                                .map(s -> Entry.combine(d, t, s))
                        )
                )
                .collect(toList());
    }

    @Value(staticConstructor = "of")
    private static class Entry {

        String input;
        String dmyPattern;
        String mdyPattern;

        public static Entry of(final String input, final String dmyPattern) {
            return new Entry(input, dmyPattern, dmyPattern);
        }

        public static Entry combine(final Entry a, final Entry b, final String pattern) {
            return new Entry(
                    a.getInput() + pattern + b.getInput(),
                    a.getDmyPattern() + pattern + b.getDmyPattern(),
                    a.getMdyPattern() + pattern + b.getMdyPattern()
            );
        }
    }
}
