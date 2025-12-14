package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidateException extends AppException {
    public ValidateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
