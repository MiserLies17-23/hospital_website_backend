package com.hospital.hospital_website.utils.security;

import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Вспомогательный компонент для управления аутентификацией
 */
@Component
public class UtilsSecurity {

    /** Объект UserRepository для поиска пользователей */
    private static UserRepository userRepository;

    /**
     * Конструктор с параметром
     *
     * @param userRepository объект UserRepository
     */
    @Autowired
    public UtilsSecurity (UserRepository userRepository) {
        UtilsSecurity.userRepository = userRepository;
    }

    /**
     * Возвращает имя пользователя из сессии или ошибку
     *
     * @return имя пользователя или ошибка
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            throw new EntityNotFoundException("Пользователь не найден!");
        return authentication.getName();
    }

    /**
     * Возвращает пользователя по имени из сессии
     *
     * @return данные пользователя или ошибка
     */
    public User getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Ошибка поиска пользователя..."));
    }

    /**
     * Обновляет пользовательские данные в сессии
     *
     * @param user данные пользователя
     */
    public void updateSecurityContext(User user) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                currentAuth.getCredentials(),
                currentAuth.getAuthorities()
        );
        newAuth.setDetails(currentAuth.getDetails());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}