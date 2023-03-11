package com.yavaarnosi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeleteResourceNotFoundException extends RuntimeException{
    public DeleteResourceNotFoundException(String message) {
        super(message);
    }

}
