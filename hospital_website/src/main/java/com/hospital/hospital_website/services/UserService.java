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
import com.hospital.hospital_website.utils.validation.Validator;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> signup(UserCreateDTO userCreateDTO) {

        userParamsValidate(userCreateDTO);

        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent())
            throw new EntityAlreadyExistsException("Пользователь с таким именем уже существует!");

        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent())
            throw new EntityAlreadyExistsException("Пользователь с таким email уже существует!");

        User user = UserMapper.userCrateDtoToUser(userCreateDTO);
        User savedUser = userRepository.save(user);
        UserResponseDTO responseDTO = UserMapper.userToUserResponseDto(savedUser);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());
        if(userOptional.isEmpty())
            throw new EntityNotFoundException("Пользователь не найден!");
        User user = userOptional.get();
        if (!user.getPassword().equals(userLoginDTO.getPassword()))
            throw new EntityNotFoundException("Неверный пароль!"); // заменить на ValidationException
        user.setVisitsCount(user.getVisitsCount() + 1);
        UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDto(user);
        session.setAttribute("user", userResponseDTO);
        userRepository.save(user);
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> checkLogin(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> logout(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null)
            throw new EntityNotFoundException("Пользователь не найден!");
        session.removeAttribute("user");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> dashboard(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null)
            throw new EntityNotFoundException("Пользователь не найден!");
        Optional<User> userOptional = userRepository.findByUsername(userResponseDTO.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserResponseDTO updatedUser = UserMapper.userToUserResponseDto(user);

            // Обновляем сессию
            session.setAttribute("user", updatedUser);

            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<?> uploadAvatar(HttpSession session, MultipartFile file) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        if (userDTO == null)
            throw new EntityNotFoundException("Пользователь не найден!");

        String username = userDTO.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String avatarUrl = UserMapper.avatarProcessing(file, username);
            user.setAvatar(avatarUrl);
            userRepository.save(user);

            UserResponseDTO updatedUser = UserMapper.userToUserResponseDto(user);
            session.setAttribute("user", updatedUser);

            return ResponseEntity.ok(avatarUrl);
        }
        return ResponseEntity.ofNullable("Ошибка при загрузке аватара");
    }

    public ResponseEntity<?>deleteAvatar(HttpSession session) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Для примера оставим!
        }

        String username = userDTO.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserMapper.deleteAvatar(user.getAvatar()); // удаляем старый аватар
            String avatarUrl = UserMapper.avatarProcessing(null, username); // загружаем дефолтный
            user.setAvatar(avatarUrl); // сохраняем дефолтный аватар для пользователя
            userRepository.save(user); // сохраняем изменения пользователя в репозиторий

            UserResponseDTO updatedUser = UserMapper.userToUserResponseDto(user);
            session.setAttribute("user", updatedUser); // Обновляем сессию

            return ResponseEntity.ok("Аватар удалён");
        }
        return ResponseEntity.ofNullable("Аватар не найден");
    }

    public ResponseEntity<?> edit(UserEditDTO userEditDTO, HttpSession session) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        Optional<User> optionalUser = userRepository.findById(userDTO.getId());
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException("Пользователь не найден!");
        User user = optionalUser.get();
        if (!(Objects.equals(user.getId(), userEditDTO.getId())))
            throw new EntityNotFoundException("Ошибка поиска пользователя");
        if(!user.getUsername().equals(userEditDTO.getUsername()))
            user.setUsername(userEditDTO.getUsername());
        if(!user.getEmail().equals(userEditDTO.getEmail()))
            user.setEmail(userEditDTO.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(UserMapper.userToUserResponseDto(user));
    }

    public void userParamsValidate(UserCreateDTO userCreateDTO) {
        Validator.usernameValidate(userCreateDTO.getUsername());
        Validator.emailValidate(userCreateDTO.getEmail());
        Validator.passwordValidate(userCreateDTO.getPassword());
    }
}