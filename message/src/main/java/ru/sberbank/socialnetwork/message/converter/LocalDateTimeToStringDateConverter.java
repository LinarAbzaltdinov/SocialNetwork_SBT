package ru.sberbank.socialnetwork.message.converter;

import org.modelmapper.AbstractConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeToStringDateConverter extends AbstractConverter<LocalDateTime, String> {

    private final DateTimeFormatter format;

    public LocalDateTimeToStringDateConverter(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
    }

    @Override
    protected String convert(LocalDateTime source) {
        String result = source == null
                        ? ""
                        : source.format(format);
        return result;
    }
}
