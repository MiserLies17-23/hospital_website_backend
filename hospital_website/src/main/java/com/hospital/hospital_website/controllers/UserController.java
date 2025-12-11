package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserEditDTO;
import com.hospital.hospital_website.dto.UserLoginDTO;
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
        return userService.signup(userCreateDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO, HttpSession session) {
        return userService.login(userLoginDTO, session);
    }

    @GetMapping("/checklogin")
    public ResponseEntity<?> checklogin(HttpSession session) {
        return userService.checkLogin(session);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserEditDTO userEditDTO, HttpSession session) {
        return userService.edit(userEditDTO, session);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        return userService.logout(session);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(HttpSession session) {
        return userService.dashboard(session);
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        return userService.uploadAvatar(session, file);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar(HttpSession session) {
        return userService.deleteAvatar(session);
    }
}
