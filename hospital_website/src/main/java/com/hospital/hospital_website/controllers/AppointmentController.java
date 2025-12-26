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

/**
 * Rest-контроллер для записей к врачам
 */
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    /** Объект AppointmentService для управления логикой операций с записями */
    private AppointmentService appointmentService;

    /**
     * Добавить новую запись
     *
     * @param request запрос на новую запись
     * @return ResponseEntity с данными новой записи
     */
    @PostMapping("/add")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequestDTO request) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.addAppointment(request);
        return ResponseEntity.ok(appointmentResponseDTO);
    }

    /**
     * Получить все занятые слоты для конкретного доктора
     *
     * @param doctorId id доктора
     * @param date дата
     * @return ResponseEntity с данными о всех занятых словах на указанную дату date для доктора с id doctorId
     */
    @GetMapping("/doctor/{doctorId}/busy-slots")
    public ResponseEntity<?> getBusySlots(@PathVariable Long doctorId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<String> busySlots = appointmentService.getBusySlots(doctorId, date);
        return ResponseEntity.ok(busySlots);
    }

    /**
     * Получить все записи
     *
     * @return ResponseEntity со списком всех записей
     */
    @GetMapping("/user")
    public ResponseEntity<?> getAppointments() {
        List<AppointmentResponseDTO> appointmentResponseDTOS = appointmentService.getAllByUser();
        return ResponseEntity.ok(appointmentResponseDTOS);
    }

    /**
     * Удалить запись по id
     *
     * @param id id записи для удаления
     * @return ResponseEntity со статусом удаления
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Запись успешно удалена!");
    }

    /**
     * Отменить запись по id
     *
     * @param id id записи для удаления
     * @return ResponseEntity с данными отменённой записи
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        AppointmentResponseDTO appointmentResponseDTO = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(appointmentResponseDTO);
    }
}
