package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;

/**
 * Класс ошибок существования сущностей
 */
public class EntityAlreadyExistsException extends AppException {

    /**
     * Конструктор с параметрами
     *
     * @param entityName тип сущности
     * @param field поле сущности
     * @param value значение поля сущности
     */
    public EntityAlreadyExistsException(String entityName, String field, String value) {
        super(String.format("%s с %s '%s' уже существует", entityName, field, value),
                HttpStatus.CONFLICT);
    }

    /**
     * Конструктор с параметром
     *
     * @param message сообщение ошибки
     */
    public EntityAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
