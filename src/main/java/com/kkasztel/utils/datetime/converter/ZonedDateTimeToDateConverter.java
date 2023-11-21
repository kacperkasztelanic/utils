package com.kkasztel.utils.datetime.converter;

import java.time.ZonedDateTime;
import java.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

    @Override
    public Date convert(final ZonedDateTime source) {
        return source == null ? null : Date.from(source.toInstant());
    }
}
