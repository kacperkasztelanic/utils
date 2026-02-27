package com.kkasztel.utils.datetime.converter;

/**
 * A generic converter functional interface.
 *
 * @param <S> the source type
 * @param <T> the target type
 */
@FunctionalInterface
public interface Converter<S, T> {

    T convert(final S source);
}
