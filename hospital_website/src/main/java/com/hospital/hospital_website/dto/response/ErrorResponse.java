package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO-ответ сообщение об ошибке
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /** Тип ошибки */
    private String error;

    /** Сообщение ошибки */
    private String message;
}
