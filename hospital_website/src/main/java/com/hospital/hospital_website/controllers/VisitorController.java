package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.services.VisitorService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
@AllArgsConstructor
public class VisitorController {

    public static VisitorService visitorService;

    // Добавить возможность выбрать доктора, записаться к доктору

    @GetMapping("/visitsCount")
    public ResponseEntity<?> updateVisitsCount(HttpSession session) {
        return visitorService.updateVisitsCount(session);
    }
}
