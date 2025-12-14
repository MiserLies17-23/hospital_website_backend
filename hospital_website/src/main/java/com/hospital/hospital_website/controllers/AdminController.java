package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.DoctorCreateDTO;
import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserEditDTO;
import com.hospital.hospital_website.services.AdminService;
import com.hospital.hospital_website.services.DoctorService;

import com.hospital.hospital_website.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AdminController {
    private AdminService adminService;
    private DoctorService doctorService;
    private UserService userService;

    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/doctors/add")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorCreateDTO doctorCreateDTO) {
        return adminService.addNewDoctor(doctorCreateDTO);
    }

    @DeleteMapping("/doctors/{doctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long doctorId) {
        return adminService.deleteDoctor(doctorId);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody UserEditDTO userEditDTO) {
        return adminService.editUser(userId, userEditDTO);
    }

    @PostMapping("/users/add")
    public ResponseEntity<?> addNewUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.signup(userCreateDTO);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return adminService.deleteUser(userId);
    }
}
