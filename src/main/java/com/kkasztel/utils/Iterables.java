package com.kkasztel.utils;

import com.kkasztel.utils.tuple.Pair;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.kkasztel.utils.Optionals.maybe;
import static com.kkasztel.utils.Optionals.none;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static lombok.AccessLevel.PRIVATE;

/**
 * Utility methods for working with {@link Iterable}s, {@link java.util.List}s,
 * {@link java.util.Set}s and {@link java.util.Map}s.
 */
@NoArgsConstructor(access = PRIVATE)
public final class Iterables {

    /** Left-associative fold over an iterable. */
    public static <T, U> U foldLeft(final Iterable<T> iterable,
                                    final U identity,
                                    final BiFunction<? super U, ? super T, ? extends U> combine) {
        requireNonNull(combine, "combine is null");
        U xs = identity;
        for (T x : iterable) {
            xs = combine.apply(xs, x);
        }
        return xs;
    }

    /** Returns the first element, throwing if the iterable is empty. */
    public static <T> T head(final Iterable<T> iterable) {
        return maybeHead(iterable).orElseThrow(() -> new IndexOutOfBoundsException("Called head on an empty iterable"));
    }

    /** Returns the first element, or {@code null} if the iterable is empty. */
    public static <T> T headOrNull(Iterable<T> iterable) {
        return maybeHead(iterable).orElse(null);
    }

    /** Returns the first element wrapped in an {@link Optional}, or empty if the iterable is empty. */
    public static <T> Optional<T> maybeHead(final Iterable<T> iterable) {
        requireNonNull(iterable, "iterable is null");
        final Iterator<T> it = iterable.iterator();
        return it.hasNext() ? maybe(it.next()) : none();
    }

    /** Zips two iterables into a list of pairs, stopping at the shorter one. */
    public static <T, U> List<Pair<T, U>> zip(final Iterable<T> first, final Iterable<U> second) {
        final Iterator<T> firstIt = first.iterator();
        final Iterator<U> secondIt = second.iterator();
        final List<Pair<T, U>> res = new ArrayList<>();
        while (firstIt.hasNext() && secondIt.hasNext()) {
            res.add(Pair.of(firstIt.next(), secondIt.next()));
        }
        return res;
    }

    /** Zips each element of the iterable with its 0-based index. */
    public static <T> List<Pair<T, Integer>> zipWithIndex(final Iterable<T> iterable) {
        final Iterator<T> it = iterable.iterator();
        final List<Pair<T, Integer>> res = new ArrayList<>();
        for (int i = 0; it.hasNext(); i++) {
            res.add(Pair.of(it.next(), i));
        }
        return res;
    }

    /** Concatenates multiple lists into one. */
    @SafeVarargs
    public static <T> List<T> combine(final List<T>... lists) {
        return Stream.of(lists).flatMap(Collection::stream).collect(toList());
    }

    /** Concatenates multiple sets into one. */
    @SafeVarargs
    public static <T> Set<T> combine(final Set<T>... sets) {
        return Stream.of(sets).flatMap(Collection::stream).collect(toSet());
    }

    /** Merges multiple maps; last-writer-wins on duplicate keys. */
    @SafeVarargs
    public static <K, V> Map<K, V> combine(final Map<K, V>... maps) {
        return combine((a, b) -> b, maps);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> combine(final BinaryOperator<V> mergeFunction, final Map<K, V>... maps) {
        return combine(mergeFunction, LinkedHashMap::new, maps);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> combine(
            final BinaryOperator<V> mergeFunction,
            final Supplier<? extends Map<K, V>> mapFactory,
            final Map<K, V>... maps) {
        requireNonNull(mergeFunction, "mergeFunction is null");
        requireNonNull(mapFactory, "mapFactory is null");
        requireNonNull(maps, "maps is null");
        return Stream.of(maps)
                .flatMap(m -> m.entrySet().stream())
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        mergeFunction,
                        mapFactory
                ));
    }

    /** Returns the symmetric difference of two sets. */
    public static <T> Set<T> differentiate(final Set<T> a, final Set<T> b) {
        requireNonNull(a, "a is null");
        requireNonNull(b, "b is null");
        return concat(
                a.stream().filter(e -> !b.contains(e)),
                b.stream().filter(e -> !a.contains(e))
        ).collect(toSet());
    }

    /** Returns all elements in {@code a} that are not in {@code b}. */
    public static <T> Set<T> subtract(final Set<T> a, final Set<T> b) {
        requireNonNull(a, "a is null");
        requireNonNull(b, "b is null");
        return a.stream()
                .filter(e -> !b.contains(e))
                .collect(toSet());
    }

    /** Returns the intersection of two sets. */
    public static <T> Set<T> intersect(final Set<T> a, final Set<T> b) {
        requireNonNull(a, "a is null");
        requireNonNull(b, "b is null");
        return a.stream()
                .filter(b::contains)
                .collect(toSet());
    }

    /** Returns a new map with keys and values swapped. Assumes values are unique. */
    public static <K, V> Map<V, K> invert(final Map<K, V> map) {
        requireNonNull(map, "map is null");
        return map.entrySet().stream().collect(toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
