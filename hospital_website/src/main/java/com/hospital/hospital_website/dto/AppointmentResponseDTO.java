package com.hospital.hospital_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    private Long id;

    private String doctorName;

    private String specialization;

    private String appointmentDate;

    private String appointmentTime;

    private String symptoms;

    private String status;
}
