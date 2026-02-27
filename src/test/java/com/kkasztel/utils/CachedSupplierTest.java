package com.kkasztel.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CachedSupplierTest {

    @Test
    void cachesTheResultOfTheDelegate() {
        final AtomicInteger counter = new AtomicInteger(0);
        final CachedSupplier<Integer> supplier = CachedSupplier.of(counter::incrementAndGet);

        assertEquals(1, supplier.get());
        assertEquals(1, supplier.get());
        assertEquals(1, supplier.get());
        assertEquals(1, counter.get());
    }

    @Test
    void recomputesAfterInvalidation() {
        final AtomicInteger counter = new AtomicInteger(0);
        final CachedSupplier<Integer> supplier = CachedSupplier.of(counter::incrementAndGet);

        assertEquals(1, supplier.get());
        supplier.invalidate();
        assertEquals(2, supplier.get());
        assertEquals(2, supplier.get());
    }

    @Test
    void multipleInvalidationsWork() {
        final AtomicInteger counter = new AtomicInteger(0);
        final CachedSupplier<Integer> supplier = CachedSupplier.of(counter::incrementAndGet);

        assertEquals(1, supplier.get());
        supplier.invalidate();
        assertEquals(2, supplier.get());
        supplier.invalidate();
        assertEquals(3, supplier.get());
        assertEquals(3, counter.get());
    }
}
