package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-ответ пользовательских данных, доступных администратору
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserResponseDTO {

    /** Уникальный id пользователя */
    private Long id;

    /** Имя пользователя */
    private String username;

    /** Пароль пользователя */
    private String password;

    /** Email пользователя */
    private String email;

    /** Роль пользователя */
    private String role;

    /** Url аватара пользователя */
    private String avatar;
}
