package com.kkasztel.utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RomanNumbers {

    private static final Map<String, Integer> FROM_ROMAN_MAP = fromRomanMap();
    private static final Map<Integer, String> TO_ROMAN_MAP = toRomanMap();
    private static final Pattern ROMAN_PATTERN = Pattern.compile("[IVXLCDM]+");

    public static String toRoman(int number) {
        if (number < 0) {
            throw new IllegalArgumentException(String.valueOf(number));
        }
        int n = number;
        StringBuilder res = new StringBuilder();
        while (n > 0) {
            Optional<Map.Entry<Integer, String>> maybeRoman = findRoman(n);
            String part = maybeRoman.map(Map.Entry::getValue).orElse("");
            n = maybeRoman.map(Map.Entry::getKey).orElse(n);
            res.append(part);
        }
        return res.toString();
    }

    public static int fromRoman(String number) {
        String normalized = number != null ? number.trim().toUpperCase() : "-";
        if (!normalized.isEmpty() && !ROMAN_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException(number);
        }
        int res = 0;
        int i = 0;
        while (i < normalized.length()) {
            String key = i + 1 < normalized.length() ?
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

    private static Optional<Map.Entry<Integer, String>> findRoman(int number) {
        return TO_ROMAN_MAP.entrySet().stream()
                .filter(e -> number - e.getKey() >= 0)
                .map(e -> (Map.Entry<Integer, String>) new SimpleEntry<>(number - e.getKey(), e.getValue()))
                .findFirst();
    }

    private static Map<String, Integer> fromRomanMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("M", 1000);
        map.put("CM", 900);
        map.put("D", 500);
        map.put("CD", 400);
        map.put("C", 100);
        map.put("XC", 90);
        map.put("L", 50);
        map.put("XL", 40);
        map.put("X", 10);
        map.put("IX", 9);
        map.put("V", 5);
        map.put("IV", 4);
        map.put("I", 1);
        return Collections.unmodifiableMap(map);
    }

    private static Map<Integer, String> toRomanMap() {
        Map<Integer, String> map = fromRomanMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        Map.Entry::getKey,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        return Collections.unmodifiableMap(map);
    }
}
