package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для изменения пользователя администратором
 */
@Data
@AllArgsConstructor
public class AdminUserEditDTO {

    /** Уникальный id пользователя */
    private Long id;

    /** Имя пользователя */
    private String username;

    /** Хешированный пароль */
    private String password;

    /** Email пользователя */
    private String email;

    /** Роль пользователя */
    private String role;
}
