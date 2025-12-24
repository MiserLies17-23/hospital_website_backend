package com.hospital.hospital_website.utils.security;

import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilsSecurity {
    private static UserRepository userRepository;

    @Autowired
    public UtilsSecurity (UserRepository userRepository) {
        UtilsSecurity.userRepository = userRepository;
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new EntityNotFoundException("Пользователь не найден!");
        return authentication.getName();
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка поиска пользователя..."));
    }
}
