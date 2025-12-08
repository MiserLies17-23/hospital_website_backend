package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserLoginDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.exception.UserFoundException;
import com.hospital.hospital_website.utils.mapper.UserMapper;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> signup(UserCreateDTO userCreateDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userCreateDTO.getUsername());
        if (userOptional.isPresent())
            throw new UserFoundException("Пользователь с таким именем уже существует!");
        if(userOptional.equals(userRepository.findByEmail(userCreateDTO.getEmail())))
            throw new UserFoundException("Пользователь с таким email уже существует!");

        User user = UserMapper.userCrateDtoToUser(userCreateDTO);
        User savedUser = userRepository.save(user);
        UserResponseDTO responseDTO = UserMapper.userToUserResponseDto(savedUser);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());
        if(userOptional.isEmpty())
            throw new UserFoundException(); // заменить на ValidationException
        User user = userOptional.get();
        if (!user.getPassword().equals(userLoginDTO.getPassword()))
            throw new UserFoundException("Неверный пароль!"); // заменить на ValidationException
        UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDto(user);
        session.setAttribute("user", userResponseDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> checkLogin(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> logout(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null)
            throw new UserFoundException();
        session.removeAttribute("user");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> dashboard(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null)
            throw new UserFoundException();
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
            throw new UserFoundException();

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
            UserMapper.deleteAvatar(user.getAvatar(), user.getUsername()); // удаляем старый аватар
            String avatarUrl = UserMapper.avatarProcessing(null, username); // загружаем дефолтный
            user.setAvatar(avatarUrl); // сохраняем дефолтный аватар для пользователя
            userRepository.save(user); // сохраняем изменения пользователя в репозиторий

            UserResponseDTO updatedUser = UserMapper.userToUserResponseDto(user);
            session.setAttribute("user", updatedUser); // Обновляем сессию

            return ResponseEntity.ok("Аватар удалён");
        }
        return ResponseEntity.ofNullable("Аватар не найден");
    }
}