package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-ответ записи к врачу
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    /** Уникальный id записи */
    private Long id;

    /** Имя врача */
    private String doctorName;

    /** Специальность врача */
    private String specialization;

    /** Дата записи */
    private String appointmentDate;

    /** Время записи */
    private String appointmentTime;

    /** Симптомы болезни */
    private String symptoms;

    /** Статус записи */
    private String status;
}
