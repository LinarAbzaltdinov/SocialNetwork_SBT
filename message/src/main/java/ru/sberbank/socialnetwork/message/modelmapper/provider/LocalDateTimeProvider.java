package ru.sberbank.socialnetwork.message.modelmapper.provider;

import org.modelmapper.AbstractProvider;
import org.modelmapper.Provider;

import java.time.LocalDateTime;

public class LocalDateTimeProvider extends AbstractProvider<LocalDateTime> {
    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
