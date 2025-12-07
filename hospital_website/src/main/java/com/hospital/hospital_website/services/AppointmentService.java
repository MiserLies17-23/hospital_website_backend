package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.AppointmentResponseDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.mapper.AppointmentMapper;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.AppointmentRepository;
import com.hospital.hospital_website.repository.DoctorRepository;
import com.hospital.hospital_website.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public ResponseEntity<?> addAppointment(AppointmentRequestDTO request, HttpSession session) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = optionalUser.get();
        Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Doctor doctor = optionalDoctor.get();

        Appointment appointment = AppointmentMapper.appointmentRequestToAppointment(request, user, doctor);
        appointmentRepository.save(appointment);

        AppointmentResponseDTO appointmentResponseDTO = AppointmentMapper.appointmentToAppointmentResponseDTO(appointment);
        return ResponseEntity.ok(appointmentResponseDTO);
    }

    public ResponseEntity<?> getBusySlots(Long doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }

        List<String> busySlots = new ArrayList<>();

        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getDate();

            if (appointmentDate != null && appointmentDate.equals(date)) {
                String time = appointment.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                if (!time.trim().isEmpty()) {
                    busySlots.add(time);
                }
            }
        }
        // Проверка на прошедшее время
        if (date.isEqual(LocalDate.now())) {
            LocalTime now = LocalTime.now();

            List<String> pastSlots = new ArrayList<>();

            // Генерируем слоты с 9:00
            LocalTime start = LocalTime.of(9, 0);

            while (start.isBefore(now)) {
                pastSlots.add(start.format(DateTimeFormatter.ofPattern("HH:mm")));
                start = start.plusMinutes(30);
            }
            busySlots.addAll(pastSlots);
        }
        return ResponseEntity.ok(busySlots);
    }

    public ResponseEntity<?> getAllByUser(HttpSession session) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = optionalUser.get();
        List<Appointment> appointments = appointmentRepository.findByUserId(user.getId());
        List<AppointmentResponseDTO> appointmentResponseDTOList = new ArrayList<>();
        for (Appointment appointment : appointments) {
            AppointmentResponseDTO appointmentResponseDTO = AppointmentMapper.appointmentToAppointmentResponseDTO(appointment);
            appointmentResponseDTOList.add(appointmentResponseDTO);
        }
        return ResponseEntity.ok(appointmentResponseDTOList);
    }

    public ResponseEntity<?> deleteAppointment(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty()) {
            return ResponseEntity.ok("Запись не найдена");
        }
        Appointment appointment = optionalAppointment.get();
        appointmentRepository.delete(appointment);
        return ResponseEntity.ok("Запись удалена!");
    }
}