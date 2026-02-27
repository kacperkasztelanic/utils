package com.kkasztel.utils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

/**
 * A thread-safe supplier that caches the result of a delegate supplier.
 * The cached value can be invalidated, causing the next call to re-evaluate the delegate.
 * <p>Note: the delegate must not return {@code null}, as null is used internally as the "not yet computed" sentinel.</p>
 *
 * @param <T> the type of the supplied value
 */
@RequiredArgsConstructor(staticName = "of")
public class CachedSupplier<T> implements Supplier<T> {

    private final Supplier<T> delegate;
    private final AtomicReference<T> value = new AtomicReference<>();

    /** Returns the cached value, computing it from the delegate if necessary. */
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

    /** Clears the cached value so it will be recomputed on the next call to {@link #get()}. */
    public void invalidate() {
        synchronized (value) {
            value.set(null);
        }
    }
}
