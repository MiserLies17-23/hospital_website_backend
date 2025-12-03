package com.hospital.hospital_website.mapper;

import com.hospital.hospital_website.dto.DoctorResponseDTO;
import com.hospital.hospital_website.models.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorMapper {

    public static DoctorResponseDTO doctorToDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getPhone()
        );
    }

    public static DoctorResponseDTO selectDoctorBySpecialization(String specialization, List<Doctor> doctors) {
        for (Doctor doctor : doctors) {
            if (specialization.equals( doctor.getSpecialization()))
                return doctorToDoctorResponseDTO(doctor);
        }
        throw new IllegalArgumentException("Specialization not found");
    }

    public static List<DoctorResponseDTO> getDTOdoctorsList (List<Doctor> doctors) {
        List<DoctorResponseDTO> doctorResponseDTOS = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorResponseDTOS.add(doctorToDoctorResponseDTO(doctor));
        }
        return doctorResponseDTOS;
    }
}
