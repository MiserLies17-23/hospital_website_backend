package com.hospital.hospital_website.mapper;

import com.hospital.hospital_website.dto.AppointmentResponseDTO;
import com.hospital.hospital_website.models.Appointment;

public class AppointmentMapper {

    public static AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getUserId(),
                appointment.getDoctorId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getSymptoms(),
                appointment.getStatus()
        );
    }
}
