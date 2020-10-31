package com.kkasztel.utils.conditional;

import java.util.function.Supplier;

import lombok.Value;

@Value(staticConstructor = "of")
public class Statement<T> {

    Supplier<Boolean> condition;
    Supplier<T> action;
}
