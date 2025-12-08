package com.hospital.hospital_website.exception;

public class UserFoundException extends EntityFoundException {

    public UserFoundException() {
        super("Пользователь не найден!");
    }

    public UserFoundException(String message) {
        super(message);
    }

    public UserFoundException(Long userId) {
        super("Пользователь", userId);
    }

}
