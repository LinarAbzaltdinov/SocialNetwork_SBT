package ru.sberbank.socialnetwork.message;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableAspectJAutoProxy
@EnableMongoRepositories
public class AppConfig {
}
