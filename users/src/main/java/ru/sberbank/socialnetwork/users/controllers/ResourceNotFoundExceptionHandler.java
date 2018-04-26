package ru.sberbank.socialnetwork.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.sberbank.socialnetwork.users.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFound() {
    }
}