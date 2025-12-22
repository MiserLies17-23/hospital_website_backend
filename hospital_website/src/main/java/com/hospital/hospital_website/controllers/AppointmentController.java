package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.response.AppointmentResponseDTO;
import com.hospital.hospital_website.services.AppointmentService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping("/add")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequestDTO request) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.addAppointment(request);
        return ResponseEntity.ok(appointmentResponseDTO);
    }

    @GetMapping("/doctor/{doctorId}/busy-slots")
    public ResponseEntity<?> getBusySlots(@PathVariable Long doctorId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<String> busySlots = appointmentService.getBusySlots(doctorId, date);
        return ResponseEntity.ok(busySlots);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAppointments() {
        List<AppointmentResponseDTO> appointmentResponseDTOS = appointmentService.getAllByUser();
        return ResponseEntity.ok(appointmentResponseDTOS);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Запись успешно удалена!");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(appointmentResponseDTO);
    }
}
