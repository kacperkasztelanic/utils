package com.kkasztel.utils.datetime.converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

    private final ZoneId zoneId;

    public static DateToZonedDateTimeConverter of() {
        return new DateToZonedDateTimeConverter(ZoneId.systemDefault());
    }

    @Override
    public ZonedDateTime convert(Date source) {
        return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), zoneId);
    }
}
