package com.kkasztel.utils.io;

/**
 * A functional interface representing a side-effectful computation that produces a value.
 *
 * @param <T> the type of the result
 */
@FunctionalInterface
public interface Effect<T> {

    T run();
}
