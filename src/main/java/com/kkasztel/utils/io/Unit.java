package com.kkasztel.utils.io;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Represents the absence of a meaningful value, analogous to {@code void} but usable as a type.
 */
@NoArgsConstructor(access = PRIVATE)
public final class Unit {

    private static final Unit INSTANCE = new Unit();

    @SuppressWarnings("all")
    public static Unit Unit() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "Unit";
    }
}
