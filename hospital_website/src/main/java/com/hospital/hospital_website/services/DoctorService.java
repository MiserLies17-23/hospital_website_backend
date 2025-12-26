package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.utils.mapper.DoctorMapper;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис, реализующий логику работы с врачами
 */
@Service
@AllArgsConstructor
public class DoctorService {

    /** Объект DoctorRepository для поиска врачей */
    private final DoctorRepository doctorRepository;

    /**
     * Возвращает всех врачей
     *
     * @return список с DTO всех врачей
     */
    public List<DoctorResponseDTO> getAllDoctors() {
        List <Doctor> doctors = doctorRepository.findAll();
        return DoctorMapper.getDTOdoctorsList(doctors);
    }
}
