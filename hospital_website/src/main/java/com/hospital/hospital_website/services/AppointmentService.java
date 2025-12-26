package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.response.AppointmentResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.enums.AppointmentStatus;
import com.hospital.hospital_website.utils.mapper.AppointmentMapper;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.AppointmentRepository;
import com.hospital.hospital_website.repository.DoctorRepository;
import com.hospital.hospital_website.repository.UserRepository;
import com.hospital.hospital_website.utils.security.UtilsSecurity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UtilsSecurity utilsSecurity;

    public AppointmentResponseDTO addAppointment(AppointmentRequestDTO request) {
        User user = utilsSecurity.getCurrentUser();
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Доктор не найден!"));
        Appointment savedAppointment = appointmentRepository.save(AppointmentMapper.appointmentRequestToAppointment(request, user, doctor));
        return AppointmentMapper.appointmentToAppointmentResponseDTO(savedAppointment);
    }

    public List<String> getBusySlots(Long doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);

        if (appointments == null || appointments.isEmpty())
            throw new EntityNotFoundException("Записи не найдены!");

        List<String> busySlots = new ArrayList<>();

        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getDate();
            if (appointmentDate != null && appointmentDate.equals(date)
                    && appointment.getStatus().equals(AppointmentStatus.SCHEDULED)) {
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

            LocalTime start = LocalTime.of(9, 0); // генерируем слоты с 9:00

            while (start.isBefore(now)) {
                pastSlots.add(start.format(DateTimeFormatter.ofPattern("HH:mm")));
                start = start.plusMinutes(30);
            }
            busySlots.addAll(pastSlots);
        }
        return busySlots;
    }

    public List<AppointmentResponseDTO> getAllByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EntityNotFoundException("Пользователь не аутентифицирован!");
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        List<Appointment> appointments = appointmentRepository.findByUserId(user.getId());
        List<AppointmentResponseDTO> appointmentResponseDTOList = new ArrayList<>();
        for (Appointment appointment : appointments) {
            checkAppointmentStatus(appointment);
            AppointmentResponseDTO appointmentResponseDTO = AppointmentMapper.appointmentToAppointmentResponseDTO(appointment);
            appointmentResponseDTOList.add(appointmentResponseDTO);
        }
        return appointmentResponseDTOList;
    }

    public void deleteAppointment(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new EntityNotFoundException("Запись", id);
        Appointment appointment = optionalAppointment.get();
        appointmentRepository.delete(appointment);
    }

    public AppointmentResponseDTO cancelAppointment(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty())
            throw new EntityNotFoundException("Запись", id);
        Appointment appointment = optionalAppointment.get();
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return AppointmentMapper.appointmentToAppointmentResponseDTO(appointmentRepository.save(appointment));
    }

    public void checkAppointmentStatus(Appointment appointment) {
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            return;
        if (appointment.getDate().isBefore(LocalDate.now()) ||
                appointment.getDate().equals(LocalDate.now()) && appointment.getTime().isBefore(LocalTime.now())) {
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.save(appointment);
        }
    }
}