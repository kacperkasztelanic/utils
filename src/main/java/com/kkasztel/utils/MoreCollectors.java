package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

/**
 * Additional {@link Collector} implementations, primarily for collecting into {@link LinkedHashMap}s
 * that preserve insertion order.
 */
@NoArgsConstructor(access = PRIVATE)
public final class MoreCollectors {

    /** Collects elements into a {@link LinkedHashMap} using the given key and value mappers. Throws on duplicate keys. */
    public static <T, K, V> Collector<T, ?, LinkedHashMap<K, V>> toLinkedMap(
            final Function<? super T, ? extends K> keyMapper,
            final Function<? super T, ? extends V> valueMapper) {
        return toLinkedMap(keyMapper, valueMapper, throwingMerger());
    }

    /** Collects elements into a {@link LinkedHashMap} with a custom merge function for duplicate keys. */
    public static <T, K, V> Collector<T, ?, LinkedHashMap<K, V>> toLinkedMap(
            final Function<? super T, ? extends K> keyMapper,
            final Function<? super T, ? extends V> valueMapper,
            final BinaryOperator<V> mergeFunction) {
        requireNonNull(keyMapper, "keyMapper");
        requireNonNull(valueMapper, "valueMapper");
        requireNonNull(mergeFunction, "mergeFunction");
        return Collector.of(
                LinkedHashMap::new,
                (map, element) -> {
                    K key = keyMapper.apply(element);
                    if (key == null) {
                        throw new NullPointerException("keyMapper returned null");
                    }
                    V value = valueMapper.apply(element);
                    putMergedAllowingNull(map, key, value, mergeFunction);
                },
                (left, right) -> {
                    right.forEach((k, v) -> putMergedAllowingNull(left, k, v, mergeFunction));
                    return left;
                }
        );
    }

    /** Collects {@link Map.Entry} elements into a {@link LinkedHashMap}. Throws on duplicate keys. */
    public static <K, V> Collector<Map.Entry<K, V>, ?, LinkedHashMap<K, V>> toLinkedMap() {
        return toLinkedMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    private static <V> BinaryOperator<V> throwingMerger() {
        return (a, b) -> {
            throw new IllegalStateException("Duplicate key encountered while collecting to LinkedHashMap");
        };
    }

    @SuppressWarnings("all")
    private static <K, V> void putMergedAllowingNull(
            final Map<K, V> map,
            final K key,
            final V value,
            final BinaryOperator<V> mergeFunction) {
        requireNonNull(map, "map is null");
        requireNonNull(key, "key is null");
        requireNonNull(mergeFunction, "mergeFunction is null");
        if (!map.containsKey(key)) {
            map.put(key, value);
            return;
        }
        map.put(key, mergeFunction.apply(map.get(key), value));
    }
}
