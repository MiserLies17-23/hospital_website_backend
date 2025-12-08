package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.AppointmentResponseDTO;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AppointmentMapper {

    public static Appointment appointmentRequestToAppointment(AppointmentRequestDTO appointmentRequestDTO,
                                                              User user, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDate(LocalDate.parse(appointmentRequestDTO.getAppointmentDate()));
        appointment.setTime(LocalTime.parse(appointmentRequestDTO.getAppointmentTime()));
        appointment.setSymptoms(appointmentRequestDTO.getSymptoms());
        appointment.setStatus(appointmentRequestDTO.getStatus());

        System.out.println("Appointment date after set: " + appointment.getDate());
        System.out.println("Appointment time after set: " + appointment.getTime());

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
                appointment.getStatus()
        );
    }
}
