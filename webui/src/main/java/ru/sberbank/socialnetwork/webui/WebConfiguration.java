package ru.sberbank.socialnetwork.webui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.sberbank.socialnetwork.webui.interceptors.AddUserInfoToModelInterceptor;
import ru.sberbank.socialnetwork.webui.interceptors.AuthInterceptor;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public AddUserInfoToModelInterceptor addUserInfoToModelInterceptor() {
        return new AddUserInfoToModelInterceptor();
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addUserInfoToModelInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index", "/login", "/signup", "/error");
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index", "/login", "/signup", "/error");
    }

}
