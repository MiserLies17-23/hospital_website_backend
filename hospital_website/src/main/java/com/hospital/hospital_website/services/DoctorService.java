package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.utils.mapper.DoctorMapper;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public ResponseEntity<?> getAllDoctors() {
        List <Doctor> doctors = doctorRepository.findAll();
        List< DoctorResponseDTO > doctorsDTO = DoctorMapper.getDTOdoctorsList(doctors);
        return ResponseEntity.ok(doctorsDTO);
    }
}
