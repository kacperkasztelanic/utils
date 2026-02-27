package com.kkasztel.utils.tuple;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PairTest {

    @Test
    void ofCreatesAPair() {
        final Pair<String, Integer> pair = Pair.of("hello", 42);
        assertEquals("hello", pair.getLeft());
        assertEquals(42, pair.getRight());
    }

    @Test
    void aliasesWork() {
        final Pair<String, Integer> pair = Pair.of("key", 1);
        assertEquals("key", pair.getFirst());
        assertEquals("key", pair.getKey());
        assertEquals(1, pair.getSecond());
        assertEquals(1, pair.getValue());
    }

    @Test
    void ofFromPairCopies() {
        final Pair<String, Integer> original = Pair.of("a", 1);
        final Pair<String, Integer> copy = Pair.of(original);
        assertEquals(original, copy);
    }

    @Test
    void ofFromMapEntry() {
        final Map.Entry<String, Integer> entry = new AbstractMap.SimpleEntry<>("x", 99);
        final Pair<String, Integer> pair = Pair.of(entry);
        assertEquals("x", pair.getLeft());
        assertEquals(99, pair.getRight());
    }

    @Test
    void equalityWorks() {
        assertEquals(Pair.of("a", 1), Pair.of("a", 1));
        assertNotEquals(Pair.of("a", 1), Pair.of("a", 2));
        assertNotEquals(Pair.of("a", 1), Pair.of("b", 1));
    }

    @Test
    void toStringDoesNotIncludeFieldNames() {
        final String s = Pair.of("a", 1).toString();
        assertTrue(s.contains("a"));
        assertTrue(s.contains("1"));
        assertFalse(s.contains("left"));
    }
}
