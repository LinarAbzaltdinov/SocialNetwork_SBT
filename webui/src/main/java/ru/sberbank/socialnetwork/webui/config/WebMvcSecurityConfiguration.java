package ru.sberbank.socialnetwork.webui.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebMvcSecurity
public class WebMvcSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // ...
}