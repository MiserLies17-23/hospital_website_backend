package com.hospital.hospital_website.exception;

import org.springframework.http.HttpStatus;

/**
 * Класс ошибок поиска сущностей
 */
public class EntityNotFoundException extends AppException {

    /**
     * Конструктор с параметрами
     *
     * @param entityName имя сущности
     * @param id id сущности
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s с ID %d не найден", entityName, id), HttpStatus.NOT_FOUND);
    }

    /**
     * Конструктор с параметром
     *
     * @param message сообщение ошибки
     */
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
