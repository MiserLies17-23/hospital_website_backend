package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.Doctor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class DoctorMapper {

    public static Doctor doctorCreateDTOToDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDTO.getName());
        doctor.setSpecialization(doctorRequestDTO.getSpecialization());
        doctor.setPhone(doctorRequestDTO.getPhone());
        return doctor;
    }

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
        throw new EntityNotFoundException("Доктора заданной специализации не найдены!");
    }

    public static List<DoctorResponseDTO> getDTOdoctorsList (List<Doctor> doctors) {
        List<DoctorResponseDTO> doctorResponseDTOS = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorResponseDTOS.add(doctorToDoctorResponseDTO(doctor));
        }
        return doctorResponseDTOS;
    }
}
