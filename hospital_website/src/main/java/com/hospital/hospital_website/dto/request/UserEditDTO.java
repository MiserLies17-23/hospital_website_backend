package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO-запрос для изменения данных пользователя
 */
@Data
@AllArgsConstructor
public class UserEditDTO {

    /** Уникальный id пользователя */
    private Long id;

    /** Имя пользователя */
    private String username;

    /** Пароль пользователя */
    private String password;

    /** Email пользователя */
    private String email;
}
