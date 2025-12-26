package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.request.UserEditDTO;
import com.hospital.hospital_website.dto.request.UserLoginDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.exception.EntityAlreadyExistsException;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.utils.mapper.UserMapper;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import com.hospital.hospital_website.utils.security.UtilsSecurity;
import com.hospital.hospital_website.utils.validation.UserParamsValidator;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Сервис, реализующий логику пользовательских операций
 */
@Service
@AllArgsConstructor
public class UserService {

    /** Объект UserRepository для поиска пользователей */
    private final UserRepository userRepository;

    /** Объект AuthenticationManager для создания сессии */
    private final AuthenticationManager authenticationManager;

    /** Объект PasswordEncoder для кодирования паролей */
    private final PasswordEncoder passwordEncoder;

    /** Объект UtilsSecurity для проверки авторизации пользователя */
    private final UtilsSecurity utilsSecurity;

    /**
     * Создание (регистрация) пользователя
     *
     * @param userCreateDTO данные пользователя для регистрации
     * @return DTO с данными зарегистрированного пользователя или ошибка
     */
    public UserResponseDTO signup(UserCreateDTO userCreateDTO) {

        UserParamsValidator.userParamsValidate(userCreateDTO);

        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent())
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже существует!");

        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent())
            throw new EntityAlreadyExistsException("Пользователь с таким email уже существует!");

        User user = UserMapper.userCrateDtoToUser(userCreateDTO);

        String encodedPassword = passwordEncoder.encode(userCreateDTO.getPassword());
        user.setPassword(encodedPassword);
        user.setAvatar(UserMapper.avatarProcessing(null, user.getUsername()));

        User savedUser = userRepository.save(user);
        return UserMapper.userToUserResponseDto(savedUser);
    }

    /**
     * Вход в систему
     *
     * @param userLoginDTO пользовательские данные для входа
     * @param session HTTP-сессия
     * @return DTO зарегистрированного пользователя
     */
    public UserResponseDTO login(UserLoginDTO userLoginDTO, HttpSession session) {

        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(),
                        userLoginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        user.setVisitsCount(user.getVisitsCount() + 1);
        User savedUser = userRepository.save(user);
        return UserMapper.userToUserResponseDto(savedUser);
    }

    /**
     * Проверка пользователя
     *
     * @return DTO с данными пользователя, если он вошёл в систему, или ошибка
     */
    public UserResponseDTO checkLogin() {
        User user = utilsSecurity.getCurrentUser();
        return UserMapper.userToUserResponseDto(user);
    }

    /**
     * Выход из системы
     *
     * @param session HTTP-сессия
     */
    public void logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        if (session != null)
            session.invalidate();
    }

    /**
     * Возвращает данные пользователя для личного кабинета
     *
     * @param session HTTP-сессия
     * @return DTO с данными пользователя или ошибка
     */
    public UserResponseDTO dashboard(HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            User user = utilsSecurity.getCurrentUser();
            return UserMapper.userToUserResponseDto(user);
        }
        throw new EntityNotFoundException("Пользователь не найден!");
    }

    /**
     * Загружает пользовательский аватар
     *
     * @param file файл-изображение для загрузки
     * @return url аватара или ошибка
     */
    public String uploadAvatar(MultipartFile file) {
        User user = utilsSecurity.getCurrentUser();
        UserMapper.deleteAvatar(user.getAvatar());
        String avatarUrl = UserMapper.avatarProcessing(file, user.getUsername());
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return avatarUrl;
    }

    /**
     * Удаляет аватар
     *
     * @return url дефолтного аватара или ошибка
     */
    public String deleteAvatar() {
        User user = utilsSecurity.getCurrentUser();
        UserMapper.deleteAvatar(user.getAvatar()); // удаляем старый аватар
        String defaultAvatarUrl = UserMapper.avatarProcessing(null, user.getUsername()); // загружаем дефолтный
        user.setAvatar(defaultAvatarUrl); // сохраняем дефолтный аватар для пользователя
        userRepository.save(user); // сохраняем изменения пользователя в репозиторий

        return defaultAvatarUrl;
    }

    /**
     * Изменяет данные пользователя
     *
     * @param userEditDTO данные пользователя для изменения
     * @return DTO с обновлёнными данными пользователя или ошибка
     */
    public UserResponseDTO edit(UserEditDTO userEditDTO) {
        User user = utilsSecurity.getCurrentUser();
        if (!(Objects.equals(user.getId(), userEditDTO.getId())))
            throw new EntityNotFoundException("Ошибка поиска пользователя");

        String oldestName = user.getUsername();
        if(!user.getUsername().equals(userEditDTO.getUsername())) {
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
        if (!Objects.equals(user.getUsername(), oldestName))
            utilsSecurity.updateSecurityContext(user);

        userRepository.save(user);
        return UserMapper.userToUserResponseDto(user);
    }
}