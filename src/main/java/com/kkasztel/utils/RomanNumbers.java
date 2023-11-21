package com.kkasztel.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.kkasztel.utils.tuple.Pair;
import lombok.NoArgsConstructor;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RomanNumbers {

    private static final Map<String, Integer> FROM_ROMAN_MAP = fromRomanMap();
    private static final Map<Integer, String> TO_ROMAN_MAP = toRomanMap();
    private static final Pattern ROMAN_PATTERN = Pattern.compile("[IVXLCDM]+");

    public static String toRoman(final int number) {
        if (number < 0) {
            throw new IllegalArgumentException(String.valueOf(number));
        }
        final StringBuilder res = new StringBuilder();
        int n = number;
        while (n > 0) {
            final Optional<Pair<Integer, String>> maybeRoman = findRoman(n);
            final String part = maybeRoman.map(Pair::getRight).orElse("");
            final int m = n;
            n = maybeRoman.map(Pair::getLeft).map(k -> m - k).orElse(n);
            res.append(part);
        }
        return res.toString();
    }

    public static int fromRoman(final String number) {
        final String normalized = number != null ? number.trim().toUpperCase() : "-";
        if (!normalized.isEmpty() && !ROMAN_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(number);
        }
        int res = 0;
        int i = 0;
        while (i < normalized.length()) {
            final String key = i + 1 < normalized.length() ?
                    String.format("%c%c", normalized.charAt(i), normalized.charAt(i + 1)) : "";
            if (FROM_ROMAN_MAP.containsKey(key)) {
                res = res + FROM_ROMAN_MAP.get(key);
                i = i + 2;
                continue;
            }
            res = res + FROM_ROMAN_MAP.get(String.valueOf(normalized.charAt(i)));
            i++;
        }
        return res;
    }

    private static Optional<Pair<Integer, String>> findRoman(final int number) {
        return TO_ROMAN_MAP.entrySet().stream()
                .filter(e -> number - e.getKey() >= 0)
                .findFirst()
                .map(Pair::of);
    }

    private static Map<String, Integer> fromRomanMap() {
        return Stream.of(
                Pair.of("M", 1000),
                Pair.of("CM", 900),
                Pair.of("D", 500),
                Pair.of("CD", 400),
                Pair.of("C", 100),
                Pair.of("XC", 90),
                Pair.of("L", 50),
                Pair.of("XL", 40),
                Pair.of("X", 10),
                Pair.of("IX", 9),
                Pair.of("V", 5),
                Pair.of("IV", 4),
                Pair.of("I", 1)
        ).collect(collectingAndThen(
                toMap(
                        Pair::getLeft,
                        Pair::getRight,
                        (a, b) -> a,
                        LinkedHashMap::new
                ),
                Collections::unmodifiableMap
        ));
    }

    private static Map<Integer, String> toRomanMap() {
        return fromRomanMap().entrySet().stream()
                .collect(collectingAndThen(
                        toMap(
                                Map.Entry::getValue,
                                Map.Entry::getKey,
                                (a, b) -> a,
                                LinkedHashMap::new
                        ),
                        Collections::unmodifiableMap
                ));
    }
}
