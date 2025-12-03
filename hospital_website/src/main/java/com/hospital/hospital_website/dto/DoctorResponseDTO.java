package com.hospital.hospital_website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponseDTO implements Serializable {

    private Long id;

    private String name;

    private String specialization;

    private String phone;
}
