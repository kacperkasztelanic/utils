package com.kkasztel.utils.datetime.converter;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor(staticName = "of")
public class DateToLocalDateConverter implements Converter<Date, LocalDate> {

    private final ZoneId zoneId;

    public static DateToLocalDateConverter of() {
        return new DateToLocalDateConverter(ZoneId.systemDefault());
    }

    @Override
    public LocalDate convert(final Date source) {
        return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), zoneId).toLocalDate();
    }
}
