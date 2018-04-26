package ru.sberbank.socialnetwork.users.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ru.sberbank.socialnetwork.users.controllers.UserRestController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append("Call method ")
                .append(joinPoint.getSignature().toShortString())
                .append(" with parameters: ");
        logger.debug(sb.append(Arrays.toString(joinPoint.getArgs()))
                .toString()
        );
    }

    @AfterReturning(pointcut = "execution(* ru.sberbank.socialnetwork.users.controllers.UserRestController.*(..)))",
            returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        StringBuilder sb = new StringBuilder();
        logger.debug(sb.append("Method ")
                .append(joinPoint.getSignature().toShortString())
                .append(" returned values: ")
                .append(result == null ? "null" : result.toString())
                .toString()
        );
    }

    @AfterThrowing(pointcut = "execution(* ru.sberbank.socialnetwork.users.controllers.UserRestController.*(..)))",
            throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        StringBuilder sb = new StringBuilder();
        logger.error(sb.append("Method ")
                .append(joinPoint.getSignature().toShortString())
                .append(" throws exception: ")
                .append(error)
                .toString()
        );
    }
}