package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Аннотация для дефолтного статуса
public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s с ID %d не найден", entityName, id), HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String entityName, String identifier) {
        super(String.format("%s '%s' не найден", entityName, identifier), HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
