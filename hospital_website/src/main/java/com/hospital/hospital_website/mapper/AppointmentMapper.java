package com.hospital.hospital_website.mapper;

import com.hospital.hospital_website.dto.AppointmentRequestDTO;
import com.hospital.hospital_website.dto.AppointmentResponseDTO;
import com.hospital.hospital_website.models.Appointment;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;

public class AppointmentMapper {

    public static Appointment appointmentRequestToAppointment(AppointmentRequestDTO appointmentRequestDTO,
                                                              User user, Doctor doctor) {
        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDate(appointmentRequestDTO.getDate());
        appointment.setTime(appointmentRequestDTO.getTime());
        appointment.setSymptoms(appointmentRequestDTO.getSymptoms());
        appointment.setStatus(appointmentRequestDTO.getStatus());
        return appointment;
    }

    public static AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getSymptoms(),
                appointment.getStatus()
        );
    }
}
