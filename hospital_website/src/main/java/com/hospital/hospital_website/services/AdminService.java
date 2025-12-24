package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.AdminUserEditDTO;
import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.dto.response.AdminUserResponseDTO;
import com.hospital.hospital_website.dto.response.DoctorResponseDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.exception.ValidateException;
import com.hospital.hospital_website.models.Doctor;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.models.enums.UserRole;
import com.hospital.hospital_website.repository.DoctorRepository;
import com.hospital.hospital_website.repository.UserRepository;
import com.hospital.hospital_website.utils.mapper.DoctorMapper;
import com.hospital.hospital_website.utils.mapper.UserMapper;
import com.hospital.hospital_website.utils.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public DoctorResponseDTO addNewDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = DoctorMapper.doctorCreateDTOToDoctor(doctorRequestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.doctorToDoctorResponseDTO(savedDoctor);
    }

    public DoctorResponseDTO getDoctorById(Long doctorId) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty())
            throw new EntityNotFoundException("Доктор не найден!");
        Doctor doctor = optionalDoctor.get();
        return DoctorMapper.doctorToDoctorResponseDTO(doctor);
    }

    public DoctorResponseDTO editDoctor(Long doctorId, DoctorRequestDTO doctorRequestDTO) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isEmpty())
            throw new EntityNotFoundException("Доктор не найден!");
        Doctor doctor = optionalDoctor.get();
        if (!Objects.equals(doctorId, doctor.getId()))
            throw new EntityNotFoundException("Ошибка поиска доктора...");
        if(!doctor.getName().equals(doctorRequestDTO.getName()))
            doctor.setName(doctorRequestDTO.getName());
        if(!doctor.getSpecialization().equals(doctorRequestDTO.getSpecialization()))
            doctor.setSpecialization(doctorRequestDTO.getSpecialization());
        if(!doctor.getPhone().equals(doctorRequestDTO.getPhone()))
            doctor.setPhone(doctorRequestDTO.getPhone());
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

    public UserResponseDTO editUser(Long id, AdminUserEditDTO userEditDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        if (!Objects.equals(id, user.getId()))
            throw new EntityNotFoundException("Ошибка поиска пользователя...");
        if (!user.getUsername().equals(userEditDTO.getUsername())) {
            Validator.usernameValidate(userEditDTO.getUsername());
            user.setUsername(userEditDTO.getUsername());
        }
        if (!Objects.equals(user.getPassword(), userEditDTO.getPassword())) {
            Validator.passwordValidate(userEditDTO.getPassword());
            user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));
        }
        if(!user.getEmail().equals(userEditDTO.getEmail())) {
            Validator.emailValidate(userEditDTO.getEmail());
            user.setEmail(userEditDTO.getEmail());
        }
        if (!EnumUtils.isValidEnum(UserRole.class, userEditDTO.getRole()))
            throw new ValidateException("Такой роли не существует!");
        user.setRole(UserRole.valueOf(userEditDTO.getRole()));
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

    public AdminUserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        return UserMapper.userToAdminUserResponseDto(user);
    }
}
