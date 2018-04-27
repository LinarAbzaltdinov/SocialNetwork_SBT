package ru.sberbank.socialnetwork.chat.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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

    @Before("execution(* ru.sberbank.socialnetwork.chat.controllers.ChatRestController.*(..))")
    public void logBeforeChatRestController(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        logger.debug(stringBuilder.append("Call method ")
                .append(joinPoint.getSignature().toShortString())
                .append(" with parameters: ")
                .append(Arrays.deepToString(joinPoint.getArgs()))
                .toString()
        );

    }

    @AfterReturning(
            pointcut = "execution(* ru.sberbank.socialnetwork.chat.controllers.ChatRestController.*(..))",
            returning = "result")
    public void logAfterChatRestController(JoinPoint joinPoint, Object result) {
        StringBuilder stringBuilder = new StringBuilder();
        logger.debug(stringBuilder.append("Method ")
                .append(joinPoint.getSignature().getName())
                .append(" returned value: ")
                .append(result == null ? "null/void" : result.toString())
                .toString()
        );
    }

    @Before("execution(* ru.sberbank.socialnetwork.chat.controllers.GroupRestController.*(..))")
    public void logBeforeGroupRestController(JoinPoint joinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        logger.debug(stringBuilder.append("Call method ")
                .append(joinPoint.getSignature().toShortString())
                .append(" with parameters: ")
                .append(Arrays.deepToString(joinPoint.getArgs()))
                .toString()
        );

    }

    @AfterReturning(
            pointcut = "execution(* ru.sberbank.socialnetwork.chat.controllers.GroupRestController.*(..))",
            returning = "result")
    public void logAfterGroupRestController(JoinPoint joinPoint, Object result) {
        StringBuilder stringBuilder = new StringBuilder();
        logger.debug(stringBuilder.append("Method ")
                .append(joinPoint.getSignature().getName())
                .append(" returned value: ")
                .append(result == null ? "null/void" : result.toString())
                .toString()
        );
    }
}