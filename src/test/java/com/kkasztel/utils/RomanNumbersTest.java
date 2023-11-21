package com.kkasztel.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RomanNumbersTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectInt(final String input, final int expected) {
        assertEquals(expected, RomanNumbers.fromRoman(input));
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectRoman(final String expected, final int input) {
        assertEquals(expected.trim().toUpperCase(), RomanNumbers.toRoman(input));
    }

    @Test
    void throwsWhenIncorrectInt() {
        assertThrows(IllegalArgumentException.class, () -> RomanNumbers.toRoman(-1));
    }

    @Test
    void throwsWhenIncorrectRoman() {
        assertThrows(IllegalArgumentException.class, () -> RomanNumbers.fromRoman(null));
        assertThrows(IllegalArgumentException.class, () -> RomanNumbers.fromRoman("A"));
        assertThrows(IllegalArgumentException.class, () -> RomanNumbers.fromRoman(" a b c "));
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> provideData() {
        return Stream.of(
                arguments("", 0),
                arguments("I", 1),
                arguments("II", 2),
                arguments("III", 3),
                arguments("IV", 4),
                arguments("V", 5),
                arguments("VI", 6),
                arguments("VII", 7),
                arguments("VIII", 8),
                arguments("IX", 9),
                arguments("X", 10),
                arguments("XI", 11),
                arguments("XII", 12),
                arguments("XIII", 13),
                arguments("XIV", 14),
                arguments("XV", 15),
                arguments("XVI", 16),
                arguments("XVII", 17),
                arguments("XVIII", 18),
                arguments("XIX", 19),
                arguments("XX", 20),
                arguments("XL", 40),
                arguments("XLIX", 49),
                arguments("L", 50),
                arguments("LI", 51),
                arguments("XC", 90),
                arguments("C", 100),
                arguments("CI", 101),
                arguments("CDXCIX", 499),
                arguments("D", 500),
                arguments("DI", 501),
                arguments("CMXCIX", 999),
                arguments("M", 1000),
                arguments("MI", 1001),
                arguments("MCMXCIX", 1999),
                arguments(" MCMXCIX ", 1999),
                arguments(" mcmxcix ", 1999),
                arguments("MM", 2000)
        );
    }
}
