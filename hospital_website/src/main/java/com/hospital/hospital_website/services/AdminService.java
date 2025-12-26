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
import com.hospital.hospital_website.repository.AppointmentRepository;
import com.hospital.hospital_website.repository.DoctorRepository;
import com.hospital.hospital_website.repository.UserRepository;
import com.hospital.hospital_website.utils.mapper.DoctorMapper;
import com.hospital.hospital_website.utils.mapper.UserMapper;
import com.hospital.hospital_website.utils.validation.DoctorParamsValidator;
import com.hospital.hospital_website.utils.validation.UserParamsValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Сервис, реализующий логику административных операций
 * Доступен только для роли ADMIN
 */
@Service
@AllArgsConstructor
@Transactional
public class AdminService {

    /** Объект UserRepository для поиска пользователей */
    private final UserRepository userRepository;

    /** Объект AppointmentRepository для поиска записей */
    private final AppointmentRepository appointmentRepository;

    /** Объект DoctorRepository для поиска врачей */
    private final DoctorRepository doctorRepository;

    /** Объект PasswordEncoder для шифрования паролей */
    private final PasswordEncoder passwordEncoder;

    /**
     * Добавляет нового врача
     *
     * @param doctorRequestDTO данные нового врача
     * @return DTO добавленного врача или ошибка
     */
    public DoctorResponseDTO addNewDoctor(DoctorRequestDTO doctorRequestDTO) {
        DoctorParamsValidator.doctorParamValidate(doctorRequestDTO);
        Doctor doctor = DoctorMapper.doctorCreateDTOToDoctor(doctorRequestDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.doctorToDoctorResponseDTO(savedDoctor);
    }

    /**
     * Возвращает врача по id
     *
     * @param doctorId id врача
     * @return DTO врача или id
     */
    public DoctorResponseDTO getDoctorById(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Доктор не найден!"));
        return DoctorMapper.doctorToDoctorResponseDTO(doctor);
    }

    /**
     * Изменяет данные врача
     *
     * @param doctorId id врача для изменения
     * @param doctorRequestDTO новые данные врача
     * @return DTO изменённого врача или ошибка
     */
    public DoctorResponseDTO editDoctor(Long doctorId, DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Доктор не найден!"));
        if (!Objects.equals(doctorId, doctor.getId()))
            throw new EntityNotFoundException("Ошибка поиска доктора...");
        if(!doctor.getName().equals(doctorRequestDTO.getName())) {
            DoctorParamsValidator.doctornameValidate(doctorRequestDTO.getName());
            doctor.setName(doctorRequestDTO.getName());
        }
        if(!doctor.getSpecialization().equals(doctorRequestDTO.getSpecialization())) {
            DoctorParamsValidator.doctorSpecializationValidate(doctorRequestDTO.getSpecialization());
            doctor.setSpecialization(doctorRequestDTO.getSpecialization());
        }
        if(!doctor.getPhone().equals(doctorRequestDTO.getPhone())) {
            DoctorParamsValidator.doctorphoneValidate(doctorRequestDTO.getPhone());
            doctor.setPhone(doctorRequestDTO.getPhone());
        }
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return DoctorMapper.doctorToDoctorResponseDTO(updatedDoctor);
    }

    /**
     * Удаляет врача по id
     *
     * @param doctorId id врача для удаления
     */
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Доктор не найден!"));
        appointmentRepository.deleteByDoctorId(doctorId);
        doctorRepository.delete(doctor);
    }

    /**
     * Возвращает всех пользователей
     *
     * @return список DTO с данными всех пользователей
     */
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

    /**
     * Изменяет пользователя по id
     *
     * @param userId id пользователя
     * @param userEditDTO новые данные пользователя
     * @return DTO с обновлёнными данными пользователя или ошибка
     */
    public UserResponseDTO editUser(Long userId, AdminUserEditDTO userEditDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        if (!Objects.equals(userId, user.getId()))
            throw new EntityNotFoundException("Ошибка поиска пользователя...");
        if (!user.getUsername().equals(userEditDTO.getUsername())) {
            UserParamsValidator.usernameValidate(userEditDTO.getUsername());
            user.setUsername(userEditDTO.getUsername());
        }
        if (!Objects.equals(user.getPassword(), userEditDTO.getPassword())) {
            UserParamsValidator.passwordValidate(userEditDTO.getPassword());
            user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));
        }
        if(!user.getEmail().equals(userEditDTO.getEmail())) {
            UserParamsValidator.emailValidate(userEditDTO.getEmail());
            user.setEmail(userEditDTO.getEmail());
        }
        if (!user.getPassword().equals(userEditDTO.getPassword())) {
            UserParamsValidator.passwordValidate(userEditDTO.getPassword());
            user.setPassword(passwordEncoder.encode(userEditDTO.getPassword()));
        }
        if (!EnumUtils.isValidEnum(UserRole.class, userEditDTO.getRole()))
            throw new ValidateException("Такой роли не существует!");
        user.setRole(UserRole.valueOf(userEditDTO.getRole()));
        User updatedUser = userRepository.save(user);
        return UserMapper.userToUserResponseDto(updatedUser);
    }

    /**
     * Удаляет пользователя по id
     *
     * @param userId id пользователя
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        appointmentRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

    /**
     * Возвращает пользователя по id
     *
     * @param userId id пользователя
     * @return DTO пользователя или ошибка
     */
    public AdminUserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        return UserMapper.userToAdminUserResponseDto(user);
    }
}
