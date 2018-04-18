package ru.sberbank.socialnetwork.message;

import org.modelmapper.*;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.sberbank.socialnetwork.message.modelmapper.converter.LocalDateTimeToStringDateConverter;
import ru.sberbank.socialnetwork.message.modelmapper.provider.LocalDateTimeProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableAspectJAutoProxy
@EnableMongoRepositories
@EnableEurekaClient
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelmapper = new ModelMapper();
        Provider<LocalDateTime> localDateTimeProvider = new LocalDateTimeProvider();
        Converter<String, LocalDateTime> toStringDate = new LocalDateTimeToStringDateConverter();
        modelmapper.createTypeMap(String.class, LocalDateTime.class);
        modelmapper.addConverter(toStringDate);
        modelmapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateTimeProvider);
        return modelmapper;
    }
}
