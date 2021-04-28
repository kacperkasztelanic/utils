package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ExcelIndex {

    private static final int NUMBER_OF_LETTERS = 'Z' - 'A' + 1;
    private static final int LETTER_A = 'A';

    public static String letters(final int index) {
        if (index < 1) {
            throw new IllegalArgumentException(String.valueOf(index));
        }
        final StringBuilder res = new StringBuilder();
        int n = index;
        while (n > 0) {
            final int remainder = (n - 1) % NUMBER_OF_LETTERS;
            n = (n - 1) / NUMBER_OF_LETTERS;
            res.append((char) (LETTER_A + remainder));
        }
        return res.reverse().toString();
    }

    public static int index(final String letters) {
        final String normalized = letters != null ? letters.trim().toUpperCase() : "";
        if (normalized.isEmpty() || !normalized.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException(letters);
        }
        return normalized.chars().reduce(0, (r, c) -> r * NUMBER_OF_LETTERS + c - LETTER_A + 1);
    }
}
