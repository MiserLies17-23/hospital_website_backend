package com.hospital.hospital_website.services;

import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Загружает пользователя по username для аутентификации.
     *
     * @param username имя пользователя
     * @return UserDetails с ролями
     * @throws EntityNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }
}
