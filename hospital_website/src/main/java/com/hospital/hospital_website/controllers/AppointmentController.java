package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.services.AppointmentService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<?> getAppointment(Long doctorId, LocalDate date,
                                            LocalTime time, String symptoms, String status, HttpSession session) {
        return appointmentService.getAppointment(doctorId, date, time, symptoms, status, session);
    }
}
