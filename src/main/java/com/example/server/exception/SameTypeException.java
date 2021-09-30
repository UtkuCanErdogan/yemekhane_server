package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SameTypeException extends RuntimeException{

    public SameTypeException(String message) {
        super(message);
    }
}
