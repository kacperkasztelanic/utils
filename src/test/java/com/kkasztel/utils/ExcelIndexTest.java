package com.kkasztel.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExcelIndexTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectIndex(final String input, final int expected) {
        assertEquals(expected, ExcelIndex.index(input));
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectLetters(final String expected, final int input) {
        assertEquals(expected.trim(), ExcelIndex.letters(input));
    }

    @Test
    void throwsWhenIncorrectIndex() {
        assertThrows(IllegalArgumentException.class, () -> ExcelIndex.letters(0));
        assertThrows(IllegalArgumentException.class, () -> ExcelIndex.letters(-1));
    }

    @Test
    void throwsWhenIncorrectLetters() {
        assertThrows(IllegalArgumentException.class, () -> ExcelIndex.index(null));
        assertThrows(IllegalArgumentException.class, () -> ExcelIndex.index(""));
        assertThrows(IllegalArgumentException.class, () -> ExcelIndex.index(" a b c "));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideData() {
        return Stream.of(
                arguments("A", 1),
                arguments("B", 2),
                arguments("AF", 32),
                arguments("DT", 124),
                arguments("AMJ", 1024),
                arguments(" AMJ ", 1024)
        );
    }
}
