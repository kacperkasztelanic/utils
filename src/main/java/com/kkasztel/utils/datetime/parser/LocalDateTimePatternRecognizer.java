package com.kkasztel.utils.datetime.parser;

import com.kkasztel.utils.CachedSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class LocalDateTimePatternRecognizer {

    private final boolean monthFirst;

    private final List<Entry> timePatterns = concat(timePatterns(), dateTimePatterns());
    private final List<Entry> datePatterns = concat(datePatterns(), dateTimePatterns());
    private final List<Entry> dateTimePatterns = dateTimePatterns();

    public static LocalDateTimePatternRecognizer dmy() {
        return new LocalDateTimePatternRecognizer(false);
    }

    public static LocalDateTimePatternRecognizer mdy() {
        return new LocalDateTimePatternRecognizer(true);
    }

    public Optional<String> recognizeTimePattern(final String str) {
        return recognizePattern(str, timePatterns);
    }

    public Optional<String> recognizeDatePattern(final String str) {
        return recognizePattern(str, datePatterns);
    }

    public Optional<String> recognizeDateTimePattern(final String str) {
        return recognizePattern(str, dateTimePatterns);
    }

    private Optional<String> recognizePattern(final String str, final List<Entry> patterns) {
        return patterns.stream()
                .filter(e -> e.matcher(str).matches())
                .findFirst()
                .map(this::getPattern);
    }

    private static <T> List<T> concat(List<T> a, List<T> b) {
        return Stream.of(a, b)
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    private List<Entry> timePatterns() {
        final List<Entry> list = new ArrayList<>();
        list.add(Entry.of("\\d{4}", "HHmm"));
        list.add(Entry.of("\\d{2}:\\d{2}", "HH:mm"));
        list.add(Entry.of("\\d{2}-\\d{2}", "HH-mm"));
        list.add(Entry.of("\\d{2}/\\d{2}", "HH/mm"));
        list.add(Entry.of("\\d{2}\\.\\d{2}", "HH.mm"));
        list.add(Entry.of("\\d{2}\\s\\d{2}", "HH mm"));
        list.add(Entry.of("\\d{6}", "HHmmss"));
        list.add(Entry.of("\\d{2}:\\d{2}:\\d{2}", "HH:mm:ss"));
        list.add(Entry.of("\\d{2}-\\d{2}-\\d{2}", "HH-mm-ss"));
        list.add(Entry.of("\\d{2}/\\d{2}/\\d{2}", "HH/mm/ss"));
        list.add(Entry.of("\\d{2}\\.\\d{2}\\.\\d{2}", "HH.mm.ss"));
        list.add(Entry.of("\\d{2}\\s\\d{2}\\s\\d{2}", "HH mm ss"));
        list.add(Entry.of("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}", "HH:mm:ss.SSS"));
        list.add(Entry.of("\\d{2}-\\d{2}-\\d{2}\\.\\d{3}", "HH-mm-ss.SSS"));
        list.add(Entry.of("\\d{2}/\\d{2}/\\d{2}\\.\\d{3}", "HH/mm/ss.SSS"));
        list.add(Entry.of("\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{3}", "HH.mm.ss.SSS"));
        list.add(Entry.of("\\d{2}\\s\\d{2}\\s\\d{2}\\.\\d{3}", "HH mm ss.SSS"));
        return Collections.unmodifiableList(list);
    }

    private List<Entry> datePatterns() {
        final List<Entry> list = new ArrayList<>();
        list.add(Entry.of("\\d{8}", "yyyyMMdd"));
        list.add(Entry.of("\\d{4}-\\d{1,2}-\\d{1,2}", "yyyy-MM-dd", "yyyy-dd-MM"));
        list.add(Entry.of("\\d{4}/\\d{1,2}/\\d{1,2}", "yyyy/MM/dd", "yyyy/dd/MM"));
        list.add(Entry.of("\\d{4}\\.\\d{1,2}\\.\\d{1,2}", "yyyy.MM.dd", "yyyy.dd.MM"));
        list.add(Entry.of("\\d{4}\\s\\d{1,2}\\s\\d{1,2}", "yyyy MM dd", "yyyy dd MM"));
        list.add(Entry.of("\\d{1,2}-\\d{1,2}-\\d{4}", "dd-MM-yyyy", "MM-dd-yyyy"));
        list.add(Entry.of("\\d{1,2}/\\d{1,2}/\\d{4}", "dd/MM/yyyy", "MM/dd/yyyy"));
        list.add(Entry.of("\\d{1,2}\\.\\d{1,2}\\.\\d{4}", "dd.MM.yyyy", "MM.dd.yyyy"));
        list.add(Entry.of("\\d{1,2}\\s\\d{1,2}\\s\\d{4}", "dd MM yyyy", "MM dd yyyy"));
        return Collections.unmodifiableList(list);
    }

    private List<Entry> dateTimePatterns() {
        final List<Entry> dates = datePatterns();
        final List<Entry> times = timePatterns();
        final List<Entry> list = new ArrayList<>();
        for (Entry d : dates) {
            for (Entry t : times) {
                list.add(Entry.combine(d, t, "", ""));
                list.add(Entry.combine(d, t, "_", "_"));
                list.add(Entry.combine(d, t, "\\s", " "));
                list.add(Entry.combine(d, t, "\\.", "."));
                list.add(Entry.combine(d, t, "-", "-"));
                list.add(Entry.combine(d, t, "/", "/"));
                list.add(Entry.combine(d, t, "T", "T"));
                list.add(Entry.combine(d, t, "", ""));
            }
        }
        return Collections.unmodifiableList(list);
    }

    private String getPattern(final Entry entry) {
        return monthFirst ? entry.getMdyPattern() : entry.getDmyPattern();
    }

    @Value
    @EqualsAndHashCode(of = "regex")
    @ToString(includeFieldNames = false, of = { "regex", "dmyPattern" })
    private static class Entry {

        String regex;
        String dmyPattern;
        String mdyPattern;
        Supplier<Pattern> pattern;

        public static Entry of(String regex, String dmyPattern) {
            return new Entry(regex, dmyPattern, dmyPattern);
        }

        public static Entry of(String regex, String dmyPattern, String mdyPattern) {
            return new Entry(regex, dmyPattern, mdyPattern);
        }

        private Entry(String regex, String dmyPattern, String mdyPattern) {
            this.regex = regex;
            this.dmyPattern = dmyPattern;
            this.mdyPattern = mdyPattern;
            this.pattern = CachedSupplier.of(() -> Pattern.compile(String.format("^%s$", regex)));
        }

        public Matcher matcher(final CharSequence sequence) {
            return pattern.get().matcher(sequence);
        }

        public static Entry combine(Entry a, Entry b, String regex, String pattern) {
            return new Entry(
                    a.getRegex() + regex + b.getRegex(),
                    a.getDmyPattern() + pattern + b.getDmyPattern(),
                    a.getMdyPattern() + pattern + b.getMdyPattern()
            );
        }
    }
}
