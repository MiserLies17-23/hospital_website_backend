package com.hospital.hospital_website.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidateException extends AppException {
    public ValidateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
