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

/**
 * Вспомогательный компонент для конвертации данных записи Appointment
 */
@Component
public abstract class AppointmentMapper {

    /**
     * Преобразует запрос AppointmentRequestDTO в сущность Appointment
     *
     * @param appointmentRequestDTO запрос с данными записи
     * @param user записанный пользователь
     * @param doctor врач
     * @return объект Appointment
     */
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

    /**
     * Преобразует сущность Appointment в DTO-ответ AppointmentResponseDTO
     *
     * @param appointment запись
     * @return DTO с данными записи
     */
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

    /**
     * Преобразует статус записи из строки в enum
     *
     * @param status строковый статус
     * @return объект AppointmentStatus
     */
    public static AppointmentStatus parseStatus(String status) {
        if (status == null || status.isEmpty())
            return AppointmentStatus.SCHEDULED;
        return AppointmentStatus.valueOf(status.toUpperCase());
    }
}
