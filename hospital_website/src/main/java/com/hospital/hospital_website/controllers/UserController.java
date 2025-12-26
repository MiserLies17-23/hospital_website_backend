package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.request.UserEditDTO;
import com.hospital.hospital_website.dto.request.UserLoginDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Rest-контроллер для пользователя
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    /** Объект UserService для управления логикой сервиса */
    private final UserService userService;

    /**
     * Регистрация пользователя
     *
     * @param userCreateDTO данные для регистрации
     * @return ResponseEntity с данными зарегистрированного пользователя
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.signup(userCreateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Вход в систему
     *
     * @param userLoginDTO данные для входа
     * @param session HTTP сессия
     * @return ResponseEntity с данными вошедшего пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        UserResponseDTO userResponseDTO = userService.login(userLoginDTO, session);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Проверка на вход
     *
     * @return ResponseEntity с ответом
     */
    @GetMapping("/checklogin")
    public ResponseEntity<?> checklogin() {
        UserResponseDTO userResponseDTO = userService.checkLogin();
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Изменить данные пользователя
     *
     * @param userEditDTO данные для изменения
     * @return ResponseEntity с изменёнными данными пользователя
     */
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserEditDTO userEditDTO) {
        UserResponseDTO userResponseDTO = userService.edit(userEditDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Выход из системы
     *
     * @param session HTTP сессия
     * @return статус выхода
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok("Вы вышли из аккаунта");
    }

    /**
     * Получить личный кабинет текущего пользователя
     *
     * @param session HTTP сессия
     * @return ResponseEntity с данными пользователя
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(HttpSession session) {
        UserResponseDTO userResponseDTO = userService.dashboard(session);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Загрузить аватар
     *
     * @param file аватар
     * @return ResponseEntity с url аватара
     */
    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = userService.uploadAvatar(file);
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", avatarUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ошибка при загрузке аватара: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Удалить аватар
     *
     * @return ResponseEntity с url дефолтного аватара
     */
    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar() {
        String defaultAvatarUrl = userService.deleteAvatar();
        return ResponseEntity.ok(defaultAvatarUrl);
    }
}
