package com.hospital.hospital_website.utils.validation;

import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.exception.ValidateException;

/**
 * Вспомогательный компонент для валидации пользовательских данных
 */
public abstract class UserParamsValidator {

    /** Шаблон для email'а пользователя */
    private static final String EMAIL_REGEX = "[a-zA-Z_]+\\w*@[a-zA-Z]+.[a-zA-Z]+";

    /** Шаблон имени пользователя */
    private static final String USERNAME_REGEX = "[a-zA-Z_]+\\w{5,}";

    /**
     * Проверяет имя пользователя на соответствие шаблону
     *
     * @param username имя пользователя для проверки
     */
    public static void usernameValidate(String username) {
        if (username.length() < 6)
            throw new ValidateException("Имя пользователя должно содержать не менее 6 символов");
        if (username.matches("[0-9]+\\w*"))
            throw new ValidateException("Имя пользователя не может начинаться с цифры!");
        if (!username.matches(USERNAME_REGEX))
            throw new ValidateException("Имя пользователя содержит недопустимые символы!");
    }

    /**
     * Проверяет email пользователя на соответствие шаблону
     *
     * @param email email пользователя для проверки
     */
    public static void emailValidate(String email) {
        if (!email.matches(EMAIL_REGEX))
            throw new ValidateException("Неправильный формат адреса электронной почты!");
    }

    /**
     * Проверяет пароль пользователя на допустимость
     *
     * @param password пароль пользователя для проверки
     */
    public static void passwordValidate(String password) {
        if (password.length() < 6)
            throw new ValidateException("Пароль не может быть короче 6 символов!");
    }

    /**
     * Проверяет все пользовательские данные на допустимость
     *
     * @param userCreateDTO пользовательские данные для проверки
     */
    public static void userParamsValidate(UserCreateDTO userCreateDTO) {
        UserParamsValidator.usernameValidate(userCreateDTO.getUsername());
        UserParamsValidator.emailValidate(userCreateDTO.getEmail());
        UserParamsValidator.passwordValidate(userCreateDTO.getPassword());
    }
}
