package com.kkasztel.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RomanNumbersTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectInt(String input, int expected) {
        assertEquals(expected, RomanNumbers.fromRoman(input));
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void returnsCorrectRoman(String expected, int input) {
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
        return Stream.of(//
                Arguments.of("", 0),//
                Arguments.of("I", 1),//
                Arguments.of("II", 2),//
                Arguments.of("III", 3),//
                Arguments.of("IV", 4),//
                Arguments.of("V", 5),//
                Arguments.of("VI", 6),//
                Arguments.of("VII", 7),//
                Arguments.of("VIII", 8),//
                Arguments.of("IX", 9),//
                Arguments.of("X", 10),//
                Arguments.of("XI", 11),//
                Arguments.of("XII", 12),//
                Arguments.of("XIII", 13),//
                Arguments.of("XIV", 14),//
                Arguments.of("XV", 15),//
                Arguments.of("XVI", 16),//
                Arguments.of("XVII", 17),//
                Arguments.of("XVIII", 18),//
                Arguments.of("XIX", 19),//
                Arguments.of("XX", 20),//
                Arguments.of("XL", 40),//
                Arguments.of("XLIX", 49),//
                Arguments.of("L", 50),//
                Arguments.of("LI", 51),//
                Arguments.of("XC", 90),//
                Arguments.of("C", 100),//
                Arguments.of("CI", 101),//
                Arguments.of("CDXCIX", 499),//
                Arguments.of("D", 500),//
                Arguments.of("DI", 501),//
                Arguments.of("CMXCIX", 999),//
                Arguments.of("M", 1000),//
                Arguments.of("MI", 1001),//
                Arguments.of("MCMXCIX", 1999),//
                Arguments.of(" MCMXCIX ", 1999),//
                Arguments.of(" mcmxcix ", 1999),//
                Arguments.of("MM", 2000)//
        );
    }
}
