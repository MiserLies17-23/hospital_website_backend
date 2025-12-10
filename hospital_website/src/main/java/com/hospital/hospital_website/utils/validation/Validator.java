package com.hospital.hospital_website.utils.validation;

public abstract class Validator {

    private static final String EMAIL_REGEX = "[a-zA-Z_]+\\w*@[a-zA-Z]+.[a-zA-Z]+";
    private static final String USERNAME_REGEX = "([a-zA-Z_]+\\w*){6,}";

    public static boolean usernameValidate(String username) {
        if (username.length() < 6)
            throw new IllegalArgumentException("Имя пользователя должно содержать не менее 6 символов");
        if (username.matches("[0-9]+\\w*"))
            throw new IllegalArgumentException("Имя пользователя не может начинаться с цифры!");
        if (!username.matches(USERNAME_REGEX))
            throw new IllegalArgumentException("Имя пользователя содержит недопустимые символы!");
        return true;
    }

    public static boolean emailValidate(String email) {
        if (!email.matches(EMAIL_REGEX))
            throw new IllegalArgumentException("Неправильный формат адреса электронной почты!");
        return email.matches(EMAIL_REGEX);
    }

    public static boolean passwordValidate(String password) {
        if (password.length() < 6)
            throw new IllegalArgumentException("Пароль не может быть короче 6 символов!");
        return true;
    }

}
