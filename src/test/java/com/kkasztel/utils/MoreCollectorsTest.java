package com.kkasztel.utils;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoreCollectorsTest {

    @Test
    void toLinkedMapPreservesInsertionOrder() {
        final List<String> input = asList("banana", "apple", "cherry");
        final LinkedHashMap<String, Integer> result = input.stream()
                .collect(MoreCollectors.toLinkedMap(s -> s, String::length));

        assertEquals(asList("banana", "apple", "cherry"), new ArrayList<>(result.keySet()));
        assertEquals(6, result.get("banana"));
        assertEquals(5, result.get("apple"));
        assertEquals(6, result.get("cherry"));
    }

    @Test
    void toLinkedMapThrowsOnDuplicateKeysByDefault() {
        final List<String> input = asList("a", "a");
        assertThrows(IllegalStateException.class,
                () -> input.stream().collect(MoreCollectors.toLinkedMap(s -> s, String::length))
        );
    }

    @Test
    void toLinkedMapWithMergeFunctionHandlesDuplicates() {
        final List<String> input = asList("a", "bb", "a");
        final LinkedHashMap<String, Integer> result = input.stream()
                .collect(MoreCollectors.toLinkedMap(s -> s, String::length, Integer::sum));

        assertEquals(2, result.get("a"));
        assertEquals(2, result.get("bb"));
    }

    @Test
    void toLinkedMapFromEntries() {
        final Stream<Map.Entry<String, Integer>> entries = Stream.of(
                new SimpleEntry<>("x", 1),
                new SimpleEntry<>("y", 2)
        );
        final LinkedHashMap<String, Integer> result = entries.collect(MoreCollectors.toLinkedMap());
        assertEquals(1, result.get("x"));
        assertEquals(2, result.get("y"));
        assertEquals(asList("x", "y"), new ArrayList<>(result.keySet()));
    }

    @Test
    void toLinkedMapResultIsLinkedHashMap() {
        final LinkedHashMap<String, Integer> result = Stream.of("a")
                .collect(MoreCollectors.toLinkedMap(s -> s, String::length));
        assertNotNull(result);
    }
}
