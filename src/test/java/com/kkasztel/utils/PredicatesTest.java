package com.kkasztel.utils;

import java.util.List;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PredicatesTest {

    @Test
    void distinctByFiltersOnExtractedKey() {
        final List<String> result = Stream.of("apple", "avocado", "banana", "blueberry", "cherry")
                .filter(Predicates.distinctBy(s -> s.charAt(0)))
                .collect(toList());
        assertEquals(3, result.size());
        assertEquals("apple", result.get(0));
        assertEquals("banana", result.get(1));
        assertEquals("cherry", result.get(2));
    }

    @Test
    void andCombinesPredicates() {
        @SuppressWarnings("unchecked")
        final Predicate<Integer> positive = Predicates.and(n -> n > 0, n -> n < 100, n -> n % 2 == 0);
        assertTrue(positive.test(10));
        assertFalse(positive.test(-1));
        assertFalse(positive.test(101));
        assertFalse(positive.test(11));
    }

    @Test
    void andWithNoPredicatesIsAlwaysTrue() {
        @SuppressWarnings("unchecked")
        final Predicate<String> always = Predicates.and();
        assertTrue(always.test("anything"));
    }

    @Test
    void orCombinesPredicates() {
        @SuppressWarnings("unchecked")
        final Predicate<String> predicate = Predicates.or(s -> s.startsWith("a"), s -> s.startsWith("b"));
        assertTrue(predicate.test("apple"));
        assertTrue(predicate.test("banana"));
        assertFalse(predicate.test("cherry"));
    }

    @Test
    void orWithNoPredicatesIsAlwaysFalse() {
        @SuppressWarnings("unchecked")
        final Predicate<String> never = Predicates.or();
        assertFalse(never.test("anything"));
    }
}
