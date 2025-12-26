package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.Doctor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный компонент для конвертации данных врача Doctor
 */
@Component
public abstract class DoctorMapper {

    /**
     * Преобразует DTO-запрос DoctorRequestDTO в сущность Doctor
     *
     * @param doctorRequestDTO DTO-запрос с данными врача
     * @return сущность Doctor
     */
    public static Doctor doctorCreateDTOToDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequestDTO.getName());
        doctor.setSpecialization(doctorRequestDTO.getSpecialization());
        doctor.setPhone(doctorRequestDTO.getPhone());
        return doctor;
    }

    /**
     * Преобразует сущность Doctor в DTO-ответ DoctorResponseDTO
     *
     * @param doctor сущность врач
     * @return DTO-ответ с данными пользователя
     */
    public static DoctorResponseDTO doctorToDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getPhone()
        );
    }

    /**
     * Преобразует список врачей в список DTO
     *
     * @param doctors список врачей
     * @return список с DTO врачей
     */
    public static List<DoctorResponseDTO> getDTOdoctorsList (List<Doctor> doctors) {
        List<DoctorResponseDTO> doctorResponseDTOS = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorResponseDTOS.add(doctorToDoctorResponseDTO(doctor));
        }
        return doctorResponseDTOS;
    }
}
