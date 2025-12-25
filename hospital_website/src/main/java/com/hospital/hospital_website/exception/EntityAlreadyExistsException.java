package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends AppException {

    public EntityAlreadyExistsException(String entityName, String field, String value) {
        super(String.format("%s с %s '%s' уже существует", entityName, field, value),
                HttpStatus.CONFLICT);
    }

    public EntityAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
