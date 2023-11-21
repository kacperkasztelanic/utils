package com.kkasztel.utils.datetime.parser;

import com.kkasztel.utils.CachedSupplier;
import com.kkasztel.utils.tuple.Pair;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class LocalDateTimePatternRecognizer {

    private final boolean monthFirst;
    private final List<Entry> dateTimePatterns;
    private final List<Entry> timePatterns;
    private final List<Entry> datePatterns;

    public static LocalDateTimePatternRecognizer dmy() {
        return new LocalDateTimePatternRecognizer(false);
    }

    public static LocalDateTimePatternRecognizer mdy() {
        return new LocalDateTimePatternRecognizer(true);
    }

    private LocalDateTimePatternRecognizer(final boolean monthFirst) {
        this.monthFirst = monthFirst;
        this.dateTimePatterns = dateTimePatterns();
        this.timePatterns = concat(timePatterns(), this.dateTimePatterns);
        this.datePatterns = concat(datePatterns(), this.dateTimePatterns);
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

    private static <T> List<T> concat(final List<T> a, final List<T> b) {
        return Stream.of(a, b)
                .flatMap(Collection::stream)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private List<Entry> timePatterns() {
        return asList(
                Entry.of("\\d{4}", "HHmm"),
                Entry.of("\\d{2}:\\d{2}", "HH:mm"),
                Entry.of("\\d{2}-\\d{2}", "HH-mm"),
                Entry.of("\\d{2}/\\d{2}", "HH/mm"),
                Entry.of("\\d{2}\\.\\d{2}", "HH.mm"),
                Entry.of("\\d{2}\\s\\d{2}", "HH mm"),
                Entry.of("\\d{6}", "HHmmss"),
                Entry.of("\\d{2}:\\d{2}:\\d{2}", "HH:mm:ss"),
                Entry.of("\\d{2}-\\d{2}-\\d{2}", "HH-mm-ss"),
                Entry.of("\\d{2}/\\d{2}/\\d{2}", "HH/mm/ss"),
                Entry.of("\\d{2}\\.\\d{2}\\.\\d{2}", "HH.mm.ss"),
                Entry.of("\\d{2}\\s\\d{2}\\s\\d{2}", "HH mm ss"),
                Entry.of("\\d{2}:\\d{2}:\\d{2}\\.\\d{3}", "HH:mm:ss.SSS"),
                Entry.of("\\d{2}-\\d{2}-\\d{2}\\.\\d{3}", "HH-mm-ss.SSS"),
                Entry.of("\\d{2}/\\d{2}/\\d{2}\\.\\d{3}", "HH/mm/ss.SSS"),
                Entry.of("\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{3}", "HH.mm.ss.SSS"),
                Entry.of("\\d{2}\\s\\d{2}\\s\\d{2}\\.\\d{3}", "HH mm ss.SSS")
        );
    }

    private List<Entry> datePatterns() {
        return asList(
                Entry.of("\\d{8}", "yyyyMMdd"),
                Entry.of("\\d{4}-\\d{1,2}-\\d{1,2}", "yyyy-MM-dd", "yyyy-dd-MM"),
                Entry.of("\\d{4}/\\d{1,2}/\\d{1,2}", "yyyy/MM/dd", "yyyy/dd/MM"),
                Entry.of("\\d{4}\\.\\d{1,2}\\.\\d{1,2}", "yyyy.MM.dd", "yyyy.dd.MM"),
                Entry.of("\\d{4}\\s\\d{1,2}\\s\\d{1,2}", "yyyy MM dd", "yyyy dd MM"),
                Entry.of("\\d{1,2}-\\d{1,2}-\\d{4}", "dd-MM-yyyy", "MM-dd-yyyy"),
                Entry.of("\\d{1,2}/\\d{1,2}/\\d{4}", "dd/MM/yyyy", "MM/dd/yyyy"),
                Entry.of("\\d{1,2}\\.\\d{1,2}\\.\\d{4}", "dd.MM.yyyy", "MM.dd.yyyy"),
                Entry.of("\\d{1,2}\\s\\d{1,2}\\s\\d{4}", "dd MM yyyy", "MM dd yyyy")
        );
    }

    private List<Pair<String, String>> separatorPatterns() {
        return asList(
                Pair.of("", ""),
                Pair.of("_", "_"),
                Pair.of("\\s", " "),
                Pair.of("\\.", "."),
                Pair.of("-", "-"),
                Pair.of("/", "/"),
                Pair.of("T", "T")
        );
    }

    private List<Entry> dateTimePatterns() {
        final List<Entry> dates = datePatterns();
        final List<Entry> times = timePatterns();
        final List<Pair<String, String>> separators = separatorPatterns();
        return dates.stream()
                .flatMap(d -> times.stream()
                        .flatMap(t -> separators.stream()
                                .map(s -> Entry.combine(d, t, s.getLeft(), s.getRight()))
                        )
                )
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
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

        public static Entry of(final String regex, final String dmyPattern) {
            return new Entry(regex, dmyPattern, dmyPattern);
        }

        public static Entry of(final String regex, final String dmyPattern, final String mdyPattern) {
            return new Entry(regex, dmyPattern, mdyPattern);
        }

        private Entry(final String regex, final String dmyPattern, final String mdyPattern) {
            this.regex = regex;
            this.dmyPattern = dmyPattern;
            this.mdyPattern = mdyPattern;
            this.pattern = CachedSupplier.of(() -> Pattern.compile("^" + regex + "$"));
        }

        public Matcher matcher(final CharSequence sequence) {
            return pattern.get().matcher(sequence);
        }

        public static Entry combine(final Entry a, final Entry b, final String regex, final String pattern) {
            return new Entry(
                    a.getRegex() + regex + b.getRegex(),
                    a.getDmyPattern() + pattern + b.getDmyPattern(),
                    a.getMdyPattern() + pattern + b.getMdyPattern()
            );
        }
    }
}
