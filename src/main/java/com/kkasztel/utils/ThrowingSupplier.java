package com.kkasztel.utils;

/**
 * A {@link java.util.function.Supplier}-like functional interface that allows throwing checked exceptions.
 *
 * @param <T> the type of the result
 * @param <E> the type of the thrown exception
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> {

    T get() throws E;
}
