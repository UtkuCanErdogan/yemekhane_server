package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OnlyHaveOneActiveCardException extends RuntimeException{
    public OnlyHaveOneActiveCardException(String message) {
        super(message);
    }
}
