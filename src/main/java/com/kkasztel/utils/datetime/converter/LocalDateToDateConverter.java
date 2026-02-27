package com.kkasztel.utils.datetime.converter;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor(staticName = "of")
public class LocalDateToDateConverter implements Converter<LocalDate, Date> {

    private final ZoneId zoneId;

    public static LocalDateToDateConverter of() {
        return new LocalDateToDateConverter(ZoneId.systemDefault());
    }

    @Override
    public Date convert(final LocalDate source) {
        return source == null ? null : Date.from(source.atStartOfDay(zoneId).toInstant());
    }
}
