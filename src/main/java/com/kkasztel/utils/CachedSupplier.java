package com.kkasztel.utils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class CachedSupplier<T> implements Supplier<T> {

    private final Supplier<T> delegate;
    private final AtomicReference<T> value = new AtomicReference<>();

    @Override
    public T get() {
        T val = value.get();
        if (val == null) {
            synchronized (value) {
                val = value.get();
                if (val == null) {
                    val = delegate.get();
                    value.set(val);
                }
            }
        }
        return val;
    }

    public synchronized void invalidate() {
        value.set(null);
    }
}
