package ru.sberbank.socialnetwork.message;

import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.sberbank.socialnetwork.message.converter.LocalDateTimeToStringDateConverter;

import java.time.LocalDateTime;

@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableMongoRepositories
@EnableEurekaClient
public class AppConfig {

    public static final String DATE_TIME_FORMAT = "dd.MM.yyyy, HH:mm:ss";

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Converter<LocalDateTime, String> localDateTimeStringConverter
                = new LocalDateTimeToStringDateConverter(DATE_TIME_FORMAT);
        modelMapper.createTypeMap(LocalDateTime.class, String.class);
        modelMapper.addConverter(localDateTimeStringConverter);
        return modelMapper;
    }
}
