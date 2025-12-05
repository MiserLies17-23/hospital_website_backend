package com.hospital.hospital_website.dto;

import com.hospital.hospital_website.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    private Long id;

    private String doctorName;

    private String specialization;

    private LocalDate date;

    private LocalTime time;

    private String symptoms;

    private String status;
}
