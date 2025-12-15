package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.request.UserEditDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
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
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public DoctorResponseDTO addNewDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = DoctorMapper.doctorCreateDTOToDoctor(doctorRequestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.doctorToDoctorResponseDTO(savedDoctor);
    }

    public DoctorResponseDTO editDoctor(Long doctorId, DoctorRequestDTO doctorRequestDTO) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty())
            throw new EntityNotFoundException("Доктор не найден!");
        Doctor doctor = optionalDoctor.get();
        if (!Objects.equals(doctorId, doctor.getId()))
            throw new EntityNotFoundException("Ошибка поиска доктора...");
        if(!doctor.getName().equals(doctorRequestDTO.getDoctorName()))
            doctor.setName(doctorRequestDTO.getDoctorName());
        if(!doctor.getSpecialization().equals(doctorRequestDTO.getDoctorSpecialization()))
            doctor.setSpecialization(doctorRequestDTO.getDoctorSpecialization());
        if(!doctor.getPhone().equals(doctorRequestDTO.getDoctorPhone()))
            doctor.setPhone(doctorRequestDTO.getDoctorPhone());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.doctorToDoctorResponseDTO(updatedDoctor);
    }

    public void deleteDoctor(Long id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty())
            throw new EntityNotFoundException("Доктор не найден!");
        Doctor doctor = optionalDoctor.get();
        doctorRepository.delete(doctor);
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty())
            throw new EntityNotFoundException("Пользователи не найдены!");
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (User user : users) {
            UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDto(user);
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    public UserResponseDTO editUser(Long id, UserEditDTO userEditDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException("Пользователь не найден!");
        User user = optionalUser.get();
        if (!Objects.equals(id, user.getId()))
            throw new EntityNotFoundException("Ошибка поиска пользователя...");
        if(!user.getUsername().equals(userEditDTO.getUsername()))
            user.setUsername(userEditDTO.getUsername());
        if(!user.getEmail().equals(userEditDTO.getEmail()))
            user.setEmail(userEditDTO.getEmail());
        User updatedUser = userRepository.save(user);
        return UserMapper.userToUserResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException("Пользователь не найден!");
        User user = optionalUser.get();
        userRepository.delete(user);
    }

}
