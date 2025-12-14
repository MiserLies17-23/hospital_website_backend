package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.DoctorCreateDTO;
import com.hospital.hospital_website.dto.UserEditDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.DoctorRepository;
import com.hospital.hospital_website.repository.UserRepository;
import com.hospital.hospital_website.utils.mapper.DoctorMapper;
import com.hospital.hospital_website.utils.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public ResponseEntity<?> addNewDoctor(DoctorCreateDTO doctorCreateDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorCreateDTO.getDoctorName());
        doctor.setSpecialization(doctorCreateDTO.getDoctorSpecialization());
        doctor.setPhone(doctorCreateDTO.getDoctorPhone());
        return ResponseEntity.ok(DoctorMapper.doctorToDoctorResponseDTO(doctor));
    }

    public ResponseEntity<?> deleteDoctor(Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()) {
            throw new EntityNotFoundException("Доктор не найден!");
        }
        Doctor doctor = optionalDoctor.get();
        doctorRepository.delete(doctor);
        return ResponseEntity.ok("Доктор удалён!");
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Пользователи не найдены!");
        }
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (User user : users) {
            UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDto(user);
            userResponseDTOS.add(userResponseDTO);
        }
        return ResponseEntity.ok(userResponseDTOS);
    }

    public ResponseEntity<?> editUser(Long id, UserEditDTO userEditDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Пользователь не найден!");
        }
        User user = optionalUser.get();
        if(!user.getUsername().equals(userEditDTO.getUsername()))
            user.setUsername(userEditDTO.getUsername());
        if(!user.getEmail().equals(userEditDTO.getEmail()))
            user.setEmail(userEditDTO.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(UserMapper.userToUserResponseDto(user));
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("Пользователь не найден!");
        }
        User user = optionalUser.get();
        userRepository.delete(user);
        return ResponseEntity.ok("Пользователь удалён!");
    }


}
