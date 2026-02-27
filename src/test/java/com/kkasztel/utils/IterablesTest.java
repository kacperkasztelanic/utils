package com.kkasztel.utils;

import com.kkasztel.utils.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IterablesTest {

    // -- foldLeft --

    @Test
    void foldLeftSumsIntegers() {
        final List<Integer> list = asList(1, 2, 3, 4);
        assertEquals(10, Iterables.foldLeft(list, 0, Integer::sum));
    }

    @Test
    void foldLeftOnEmptyReturnsIdentity() {
        assertEquals("start", Iterables.foldLeft(emptyList(), "start", (a, b) -> a + b));
    }

    @Test
    void foldLeftConcatenatesStrings() {
        final List<String> list = asList("a", "b", "c");
        assertEquals("abc", Iterables.foldLeft(list, "", String::concat));
    }

    // -- head / headOrNull / maybeHead --

    @Test
    void headReturnsFirstElement() {
        assertEquals("a", Iterables.head(asList("a", "b", "c")));
    }

    @Test
    void headThrowsOnEmptyIterable() {
        assertThrows(IndexOutOfBoundsException.class, () -> Iterables.head(emptyList()));
    }

    @Test
    void headOrNullReturnsFirstElement() {
        assertEquals("a", Iterables.headOrNull(asList("a", "b")));
    }

    @Test
    void headOrNullReturnsNullOnEmpty() {
        assertNull(Iterables.headOrNull(emptyList()));
    }

    @Test
    void maybeHeadReturnsPresentForNonEmpty() {
        assertEquals(Optional.of(1), Iterables.maybeHead(singletonList(1)));
    }

    @Test
    void maybeHeadReturnsEmptyForEmpty() {
        assertEquals(Optional.empty(), Iterables.maybeHead(emptyList()));
    }

    // -- zip --

    @Test
    void zipPairsElements() {
        final List<Pair<String, Integer>> result = Iterables.zip(asList("a", "b", "c"), asList(1, 2, 3));
        assertEquals(3, result.size());
        assertEquals(Pair.of("a", 1), result.get(0));
        assertEquals(Pair.of("c", 3), result.get(2));
    }

    @Test
    void zipStopsAtShorterIterable() {
        final List<Pair<String, Integer>> result = Iterables.zip(asList("a", "b"), asList(1, 2, 3));
        assertEquals(2, result.size());
    }

    @Test
    void zipWithIndexAssignsIndices() {
        final List<Pair<String, Integer>> result = Iterables.zipWithIndex(asList("x", "y", "z"));
        assertEquals(Pair.of("x", 0), result.get(0));
        assertEquals(Pair.of("y", 1), result.get(1));
        assertEquals(Pair.of("z", 2), result.get(2));
    }

    // -- combine lists / sets / maps --

    @Test
    void combineLists() {
        final List<Integer> result = Iterables.combine(asList(1, 2), asList(3, 4));
        assertEquals(asList(1, 2, 3, 4), result);
    }

    @Test
    void combineSets() {
        final Set<Integer> result = Iterables.combine(setOf(1, 2), setOf(2, 3));
        assertEquals(setOf(1, 2, 3), result);
    }

    @Test
    void combineMapsLastWriterWins() {
        final Map<String, Integer> a = mapOf("x", 1);
        final Map<String, Integer> b = mapOf("x", 2);
        final Map<String, Integer> result = Iterables.combine(a, b);
        assertEquals(2, result.get("x"));
    }

    // -- set operations --

    @Test
    void differentiateReturnsSymmetricDifference() {
        final Set<Integer> a = setOf(1, 2, 3);
        final Set<Integer> b = setOf(2, 3, 4);
        assertEquals(setOf(1, 4), Iterables.differentiate(a, b));
    }

    @Test
    void subtractReturnsElementsOnlyInFirst() {
        final Set<Integer> a = setOf(1, 2, 3);
        final Set<Integer> b = setOf(2, 3, 4);
        assertEquals(setOf(1), Iterables.subtract(a, b));
    }

    @Test
    void intersectReturnsCommonElements() {
        final Set<Integer> a = setOf(1, 2, 3);
        final Set<Integer> b = setOf(2, 3, 4);
        assertEquals(setOf(2, 3), Iterables.intersect(a, b));
    }

    // -- invert --

    @Test
    void invertSwapsKeysAndValues() {
        final Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        final Map<Integer, String> result = Iterables.invert(map);
        assertEquals("a", result.get(1));
        assertEquals("b", result.get(2));
    }

    // -- helpers --

    @SafeVarargs
    private static <T> Set<T> setOf(final T... items) {
        return new HashSet<>(Arrays.asList(items));
    }

    private static <K, V> Map<K, V> mapOf(final K key, final V value) {
        final Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
