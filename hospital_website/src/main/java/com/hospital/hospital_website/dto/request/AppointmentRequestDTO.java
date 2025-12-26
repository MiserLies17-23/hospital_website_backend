package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-запрос для записи к врачу
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {

    /** Уникальный id доктора */
    private Long doctorId;

    /** Дата записи */
    private String appointmentDate;

    /** Время записи */
    private String appointmentTime;

    /** Симптомы болезни */
    private String symptoms;

    /** Статус записи */
    private String status;
}
