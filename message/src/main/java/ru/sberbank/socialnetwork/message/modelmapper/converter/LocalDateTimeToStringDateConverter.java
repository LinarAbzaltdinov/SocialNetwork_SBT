package ru.sberbank.socialnetwork.message.modelmapper.converter;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeToStringDateConverter extends AbstractConverter<String, LocalDateTime> {
    @Override
    protected LocalDateTime convert(String source) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(source, format);
        return localDateTime;
    }
}
