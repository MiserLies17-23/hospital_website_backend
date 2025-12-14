package com.hospital.hospital_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorCreateDTO {

    private String doctorName;

    private String doctorSpecialization;

    private String doctorPhone;
}
