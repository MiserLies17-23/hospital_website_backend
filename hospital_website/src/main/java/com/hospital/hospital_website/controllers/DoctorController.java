package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest-контроллер для общедоступных данных о докторах
 */
@RestController
@AllArgsConstructor
public class DoctorController {

    /** Объект DoctorService для управления логикой работы с врачами */
    private final DoctorService doctorService;

    /**
     * Контроллер для получения данных обо всех докторах
     *
     * @return ResponseEntity с данными всех докторов
     */
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors() {
        List<DoctorResponseDTO> allDoctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(allDoctors);
    }
}
