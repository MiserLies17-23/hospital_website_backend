package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.request.UserEditDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.services.AdminService;
import com.hospital.hospital_website.services.DoctorService;

import com.hospital.hospital_website.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private AdminService adminService;
    private DoctorService doctorService;
    private UserService userService;

    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        List<DoctorResponseDTO> allDoctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(allDoctors);
    }

    @PostMapping("/doctors/add")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = adminService.addNewDoctor(doctorRequestDTO);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    @PostMapping("/doctors/{doctorId}/edit")
    public ResponseEntity<?> editDoctor(@PathVariable Long doctorId, @RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = adminService.editDoctor(doctorId, doctorRequestDTO);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    @DeleteMapping("/doctors/{doctorId}/delete")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long doctorId) {
        adminService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Доктор успешно удалён!");
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO = adminService.getUserById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/users/{userId}/edit")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody UserEditDTO userEditDTO) {
        UserResponseDTO userResponseDTO = adminService.editUser(userId, userEditDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/users/add")
    public ResponseEntity<?> addNewUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.signup(userCreateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/users/{userId}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }
}
