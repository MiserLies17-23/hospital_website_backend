package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO-ответ с данными пользователя
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    /** Уникальный id пользователя */
    private Long id;

    /** Имя пользователя */
    private String username;

    /** Email пользователя */
    private String email;

    /** Роль пользователя */
    private String role;

    /** Аватар пользователя */
    private String avatar;

    /** Количество посещений пользователя */
    private int visitsCount;
}
