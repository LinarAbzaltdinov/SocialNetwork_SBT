package ru.sberbank.socialnetwork.message;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableAspectJAutoProxy
@EnableMongoRepositories
@EnableEurekaClient
public class AppConfig {
}
