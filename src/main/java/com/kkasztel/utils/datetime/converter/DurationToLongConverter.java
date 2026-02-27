package com.kkasztel.utils.datetime.converter;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor(staticName = "of")
public class DurationToLongConverter implements Converter<Duration, Long> {

    @Override
    public Long convert(final Duration source) {
        return source == null ? null : source.toNanos();
    }
}
