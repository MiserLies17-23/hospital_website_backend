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
}