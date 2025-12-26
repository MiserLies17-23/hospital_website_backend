package com.hospital.hospital_website.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Абстрактный класс ошибок веб-приложения
 */
@Getter
public abstract class AppException extends RuntimeException {

    /** HTTP-статус ошибки */
    private final HttpStatus httpStatus;

    /**
     * Конструктор с параметрами
     *
     * @param message сообщение о конкретной ошибке
     * @param httpStatus статус ошибки
     */
    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}