package com.kkasztel.utils.datetime.converter;

import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor(staticName = "of")
public class LongToDurationConverter implements Converter<Long, Duration> {

    @Override
    public Duration convert(final Long source) {
        return source == null ? null : Duration.ofNanos(source);
    }
}
