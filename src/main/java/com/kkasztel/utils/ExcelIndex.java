package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Converts between 1-based numeric column indices and Excel-style alphabetical column labels
 * (e.g. 1 &harr; "A", 27 &harr; "AA").
 */
@NoArgsConstructor(access = PRIVATE)
public final class ExcelIndex {

    private static final int NUMBER_OF_LETTERS = 'Z' - 'A' + 1;
    private static final int LETTER_A = 'A';

    /**
     * Converts a 1-based column index to its Excel-style letter representation.
     *
     * @param index the column index (must be &gt;= 1)
     * @return the letter representation (e.g. 1 &rarr; "A", 28 &rarr; "AB")
     * @throws IllegalArgumentException if index &lt; 1
     */
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

    /**
     * Converts an Excel-style letter label to its 1-based column index.
     *
     * @param letters the column label (e.g. "A", "AB")
     * @return the 1-based column index
     * @throws IllegalArgumentException if the input is null, blank, or contains non-letter characters
     */
    public static int index(final String letters) {
        final String normalized = letters != null ? letters.trim().toUpperCase() : "";
        if (normalized.isEmpty() || !normalized.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException(letters);
        }
        return normalized.chars().reduce(0, (r, c) -> r * NUMBER_OF_LETTERS + c - LETTER_A + 1);
    }
}
