package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.AdminUserEditDTO;
import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.response.AdminUserResponseDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.services.AdminService;
import com.hospital.hospital_website.services.DoctorService;

import com.hospital.hospital_website.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-контроллер для администратора
 * Предоставляет расширенный доступ к данным пользователей и врачей
 */
@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    /** Объект AdminService для управления логикой административных операций */
    private AdminService adminService;

    /** Объект DoctorService для управления логикой операций с врачами */
    private DoctorService doctorService;

    /** Объект UserService для управления логикой операций с пользователями */
    private UserService userService;

    /**
     * Получить список с данными всех врачей
     *
     * @return ResponseEntity с данными всех врачей
     */
    @GetMapping("/doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllDoctors() {
        List<DoctorResponseDTO> allDoctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(allDoctors);
    }

    /**
     * Получить данные конкретного врача по id
     *
     * @param doctorId id доктора
     * @return ResponseEntity с данными врача
     */
    @GetMapping("/doctors/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDoctorById(@PathVariable Long doctorId) {
        DoctorResponseDTO doctorResponseDTO = adminService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    /**
     * Добавить нового врача
     *
     * @param doctorRequestDTO данные нового врача
     * @return ResponseEntity с данными добавленного врача
     */
    @PostMapping("/doctors/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addDoctor(@RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = adminService.addNewDoctor(doctorRequestDTO);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    /**
     * Изменить данные врача
     *
     * @param doctorId id доктора
     * @param doctorRequestDTO новые данные доктора
     * @return ResponseEntity с новыми данными врача
     */
    @PostMapping("/doctors/{doctorId}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editDoctor(@PathVariable Long doctorId, @RequestBody DoctorRequestDTO doctorRequestDTO) {
        DoctorResponseDTO doctorResponseDTO = adminService.editDoctor(doctorId, doctorRequestDTO);
        return ResponseEntity.ok(doctorResponseDTO);
    }

    /**
     * Удалить врача по id
     *
     * @param doctorId id доктора
     * @return ResponseEntity с сообщением о статусе удаления
     */
    @DeleteMapping("/doctors/{doctorId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long doctorId) {
        adminService.deleteDoctor(doctorId);
        return ResponseEntity.ok("Доктор успешно удалён!");
    }

    /**
     * Получить данные всех пользователей
     *
     * @return ResponseEntity с данными всех пользователей
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    /**
     * Получить данные конкретного пользователя по id
     *
     * @param userId id пользователя
     * @return ResponseEntity с данными конкретного врача
     */
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        AdminUserResponseDTO userResponseDTO = adminService.getUserById(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Изменить данные пользователя
     *
     * @param userId id пользователя
     * @param userEditDTO новые пользовательские данные
     * @return ResponseEntity с данными всех врачей
     */
    @PostMapping("/users/{userId}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editUser(@PathVariable Long userId, @RequestBody AdminUserEditDTO userEditDTO) {
        UserResponseDTO userResponseDTO = adminService.editUser(userId, userEditDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Добавить нового пользователя
     *
     * @param userCreateDTO данные нового пользователя
     * @return ResponseEntity с данными нового пользователя
     */
    @PostMapping("/users/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewUser(@RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO userResponseDTO = userService.signup(userCreateDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    /**
     * Удалить пользователя по id
     *
     * @param userId id пользователя для удаления
     * @return ResponseEntity со статусом удаления
     */
    @DeleteMapping("/users/{userId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }
}
