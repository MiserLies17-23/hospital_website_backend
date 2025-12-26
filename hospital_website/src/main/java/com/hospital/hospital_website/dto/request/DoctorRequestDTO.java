package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-запрос для врача
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequestDTO {

    /** ФИО врача */
    private String name;

    /** Специальность врача */
    private String specialization;

    /** Номер телефона врача */
    private String phone;
}
