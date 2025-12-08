package com.hospital.hospital_website.exception;

public class EntityFoundException extends AppException {

    public EntityFoundException(String message) {
        super(message);
    }

    public EntityFoundException(String entityName, Long id) {
        super(entityName + " c id: " + id + " не найден!");
    }
}
