package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO-ответ данных врача
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO {

    /** Уникальный id врача */
    private Long id;

    /** ФИО врача */
    private String name;

    /** Специальность врача */
    private String specialization;

    /** Телефон врача */
    private String phone;
}
