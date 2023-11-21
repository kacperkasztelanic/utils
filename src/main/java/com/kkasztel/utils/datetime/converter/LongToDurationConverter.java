package com.kkasztel.utils.datetime.converter;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class LongToDurationConverter implements Converter<Long, Duration> {

    @Override
    public Duration convert(final Long source) {
        return source == null ? null : Duration.ofNanos(source);
    }
}
