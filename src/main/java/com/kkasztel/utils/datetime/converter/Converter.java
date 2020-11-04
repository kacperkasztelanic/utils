package com.kkasztel.utils.datetime.converter;

@FunctionalInterface
public interface Converter<S, T> {

    T convert(S source);
}
