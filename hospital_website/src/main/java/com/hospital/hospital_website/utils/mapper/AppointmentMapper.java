package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.response.AppointmentResponseDTO;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.enums.AppointmentStatus;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public abstract class AppointmentMapper {

    public static Appointment appointmentRequestToAppointment(AppointmentRequestDTO appointmentRequestDTO,
                                                              User user, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDate(LocalDate.parse(appointmentRequestDTO.getAppointmentDate()));
        appointment.setTime(LocalTime.parse(appointmentRequestDTO.getAppointmentTime()));
        appointment.setSymptoms(appointmentRequestDTO.getSymptoms());
        appointment.setStatus(parseStatus(appointmentRequestDTO.getStatus()));

        return appointment;
    }

    public static AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                appointment.getTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                appointment.getSymptoms(),
                appointment.getStatus().name()
        );
    }

    public static AppointmentStatus parseStatus(String status) {
        if (status == null || status.isEmpty())
            return AppointmentStatus.SCHEDULED;
        return AppointmentStatus.valueOf(status.toUpperCase());
    }
}
