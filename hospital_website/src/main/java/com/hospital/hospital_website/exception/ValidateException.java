package com.hospital.hospital_website.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Класс ошибок валидации
 */
@Getter
public class ValidateException extends AppException {

    /**
     * Конструктор с параметром
     *
     * @param message сообщение ошибки
     */
    public ValidateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
