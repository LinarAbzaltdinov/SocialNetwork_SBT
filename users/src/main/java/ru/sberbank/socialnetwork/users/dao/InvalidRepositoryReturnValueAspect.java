package ru.sberbank.socialnetwork.users.dao;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.sberbank.socialnetwork.users.exceptions.ResourceNotFoundException;

@Aspect
@Component
public class InvalidRepositoryReturnValueAspect {
    @AfterReturning(pointcut = "execution(* ru.sberbank.socialnetwork.users.dao.*Repository+.findOne(..))",
            returning = "result")
    public void intercept(final Object result) {
        if (result == null) {
            throw new ResourceNotFoundException();
        }
    }
}