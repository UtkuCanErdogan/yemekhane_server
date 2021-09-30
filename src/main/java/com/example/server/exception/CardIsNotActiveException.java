package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CardIsNotActiveException extends RuntimeException{
    public CardIsNotActiveException(String message) {
        super(message);
    }
}
