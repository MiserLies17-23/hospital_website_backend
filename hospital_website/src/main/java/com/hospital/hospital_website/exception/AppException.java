package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

//    public AppException(String message, Throwable cause) {
//        super(message, cause);
//    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}