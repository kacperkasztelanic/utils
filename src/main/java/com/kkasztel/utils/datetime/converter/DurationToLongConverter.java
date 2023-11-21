package com.kkasztel.utils.datetime.converter;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class DurationToLongConverter implements Converter<Duration, Long> {

    @Override
    public Long convert(final Duration source) {
        return source == null ? null : source.toNanos();
    }
}
