package com.hospital.hospital_website.dto;

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
public class AppointmentRequestDTO {

    private Long doctorId;

    private LocalDate date;

    private LocalTime time;

    private String symptoms;

    private String status;
}
