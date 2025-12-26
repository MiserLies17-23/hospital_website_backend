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

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UtilsSecurity utilsSecurity;

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

    public UserResponseDTO login(UserLoginDTO userLoginDTO, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(),
                        userLoginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден!"));
        user.setVisitsCount(user.getVisitsCount() + 1);
        User savedUser = userRepository.save(user);
        return UserMapper.userToUserResponseDto(savedUser);
    }

    public UserResponseDTO checkLogin() {
        User user = utilsSecurity.getCurrentUser();
        return UserMapper.userToUserResponseDto(user);
    }

    public void logout(HttpSession session) {
        SecurityContextHolder.clearContext();
        if (session != null)
            session.invalidate();
    }

    public UserResponseDTO dashboard(HttpSession session) {
        if (session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            User user = utilsSecurity.getCurrentUser();
            return UserMapper.userToUserResponseDto(user);
        }
        throw new EntityNotFoundException("Пользователь не найден!");
    }

    public String uploadAvatar(MultipartFile file) {
        User user = utilsSecurity.getCurrentUser();
        UserMapper.deleteAvatar(user.getAvatar());
        String avatarUrl = UserMapper.avatarProcessing(file, user.getUsername());
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return avatarUrl;
    }

    public String deleteAvatar() {
        User user = utilsSecurity.getCurrentUser();
        UserMapper.deleteAvatar(user.getAvatar()); // удаляем старый аватар
        String defaultAvatarUrl = UserMapper.avatarProcessing(null, user.getUsername()); // загружаем дефолтный
        user.setAvatar(defaultAvatarUrl); // сохраняем дефолтный аватар для пользователя
        userRepository.save(user); // сохраняем изменения пользователя в репозиторий

        return defaultAvatarUrl;
    }

    public UserResponseDTO edit(UserEditDTO userEditDTO) {
        User user = utilsSecurity.getCurrentUser();
        if (!(Objects.equals(user.getId(), userEditDTO.getId())))
            throw new EntityNotFoundException("Ошибка поиска пользователя");
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
        userRepository.save(user);
        return UserMapper.userToUserResponseDto(user);
    }


}