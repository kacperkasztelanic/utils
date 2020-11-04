package com.kkasztel.utils.datetime.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    private final ZoneId zoneId;

    public static DateToLocalDateTimeConverter of() {
        return new DateToLocalDateTimeConverter(ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime convert(Date source) {
        return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), zoneId).toLocalDateTime();
    }
}
