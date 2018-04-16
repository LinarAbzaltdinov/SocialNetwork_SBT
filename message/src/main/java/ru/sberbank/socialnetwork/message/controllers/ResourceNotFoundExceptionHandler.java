package ru.sberbank.socialnetwork.message.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.socialnetwork.message.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {
    public static final String ERROR_MSG = "Resource Not Found";
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleResourceNotFound() {
        return ERROR_MSG;
    }
}
