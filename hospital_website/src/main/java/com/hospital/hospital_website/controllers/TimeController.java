package com.hospital.hospital_website.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Rest-контроллер для получения серверного времени
 */
@RestController
public class TimeController {

    /**
     * Получить серверное время
     *
     * @return ResponseEntity с отформатированным серверным временем
     */
    @GetMapping("/time")
    public ResponseEntity<String> getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return ResponseEntity.ok(now.format(formatter));
    }
}
