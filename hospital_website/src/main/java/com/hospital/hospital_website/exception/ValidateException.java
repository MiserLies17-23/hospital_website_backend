package com.hospital.hospital_website.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidateException extends AppException {
    public ValidateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
