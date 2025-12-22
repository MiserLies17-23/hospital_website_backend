package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.request.UserEditDTO;
import com.hospital.hospital_website.dto.request.UserLoginDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.signup(userCreateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        UserResponseDTO userResponseDTO = userService.login(userLoginDTO, session);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/checklogin")
    public ResponseEntity<?> checklogin() {
        UserResponseDTO userResponseDTO = userService.checkLogin();
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserEditDTO userEditDTO) {
        UserResponseDTO userResponseDTO = userService.edit(userEditDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok("Вы вышли из аккаунта");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        UserResponseDTO userResponseDTO = userService.dashboard();
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        String avatarUrl = userService.uploadAvatar(file);
        return ResponseEntity.ok(avatarUrl);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar() {
        String defaultAvatarUrl = userService.deleteAvatar();
        return ResponseEntity.ok(defaultAvatarUrl);
    }
}
