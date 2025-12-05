package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.AppointmentRequestDTO;
import com.hospital.hospital_website.services.AppointmentService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping("/add")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequestDTO request, HttpSession session) {
        return appointmentService.addAppointment(request, session);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAppointments(HttpSession session) {
        return appointmentService.getAllByUser(session);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id, HttpSession session) {
        return appointmentService.deleteAppointment(id, session);
    }
}
