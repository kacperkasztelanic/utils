package com.kkasztel.utils.datetime.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

    private final ZoneId zoneId;

    public static LocalDateTimeToDateConverter of() {
        return new LocalDateTimeToDateConverter(ZoneId.systemDefault());
    }

    @Override
    public Date convert(final LocalDateTime source) {
        return source == null ? null : Date.from(source.atZone(zoneId).toInstant());
    }
}
