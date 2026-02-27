package com.kkasztel.utils.datetime.converter;

import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor(staticName = "of")
public class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

    private final ZoneId zoneId;

    public static DateToZonedDateTimeConverter of() {
        return new DateToZonedDateTimeConverter(ZoneId.systemDefault());
    }

    @Override
    public ZonedDateTime convert(final Date source) {
        return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), zoneId);
    }
}
