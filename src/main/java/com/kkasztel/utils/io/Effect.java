package com.kkasztel.utils.io;

@FunctionalInterface
public interface Effect<T> {

    T run();
}
