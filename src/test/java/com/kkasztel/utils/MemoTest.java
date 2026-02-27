package com.kkasztel.utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MemoTest {

    @Test
    void memoizedFunctionCachesResults() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Function<String, Integer> f = Memo.memoize(s -> {
            counter.incrementAndGet();
            return s.length();
        });

        assertEquals(3, f.apply("abc"));
        assertEquals(3, f.apply("abc"));
        assertEquals(3, f.apply("abc"));
        assertEquals(1, counter.get());
    }

    @Test
    void memoizedFunctionCachesDifferentKeys() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Function<Integer, Integer> f = Memo.memoize(n -> {
            counter.incrementAndGet();
            return n * n;
        });

        assertEquals(1, f.apply(1));
        assertEquals(4, f.apply(2));
        assertEquals(9, f.apply(3));
        assertEquals(1, f.apply(1));
        assertEquals(3, counter.get());
    }
}
