package com.hospital.hospital_website.utils.validation;

import com.hospital.hospital_website.exception.ValidateException;

public abstract class Validator {

    private static final String EMAIL_REGEX = "[a-zA-Z_]+\\w*@[a-zA-Z]+.[a-zA-Z]+";
    private static final String USERNAME_REGEX = "[a-zA-Z_]+\\w{5,}";

    public static void usernameValidate(String username) {
        if (username.length() < 6)
            throw new ValidateException("Имя пользователя должно содержать не менее 6 символов");
        if (username.matches("[0-9]+\\w*"))
            throw new ValidateException("Имя пользователя не может начинаться с цифры!");
        if (!username.matches(USERNAME_REGEX))
            throw new ValidateException("Имя пользователя содержит недопустимые символы!");
    }

    public static void emailValidate(String email) {
        if (!email.matches(EMAIL_REGEX))
            throw new ValidateException("Неправильный формат адреса электронной почты!");
    }

    public static void passwordValidate(String password) {
        if (password.length() < 6)
            throw new ValidateException("Пароль не может быть короче 6 символов!");
    }

}
