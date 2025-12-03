package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.AppointmentResponseDTO;
import com.hospital.hospital_website.mapper.AppointmentMapper;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.AppointmentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    public ResponseEntity<?> getAppointment(Long doctorId, LocalDate date, LocalTime time,
                                            String symptoms, String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Appointment appointment = new Appointment();
        appointment.setUserId(user.getId());
        appointment.setDoctorId(doctorId);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setSymptoms(symptoms);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        AppointmentResponseDTO appointmentResponseDTO = AppointmentMapper.appointmentToAppointmentResponseDTO(appointment);
        return ResponseEntity.ok(appointmentResponseDTO);
    }
}
