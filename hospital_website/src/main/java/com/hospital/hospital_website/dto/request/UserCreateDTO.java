package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-запрос для создания (регистрации) пользователя
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    /** Имя пользователя */
    private String username;

    /** Email пользователя */
    private String email;

    /** Пароль пользователя */
    private String password;

}
