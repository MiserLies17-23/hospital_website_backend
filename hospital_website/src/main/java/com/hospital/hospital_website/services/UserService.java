package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserLoginDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.mapper.UserMapper;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

import static com.hospital.hospital_website.mapper.UserMapper.DEFAULT_USER_IMAGE_URL;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> signup(UserCreateDTO userCreateDTO) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(userCreateDTO.getUsername());
            if (userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Username already exists"));
            }

            User user = UserMapper.userCrateDtoToUser(userCreateDTO);
            User savedUser = userRepository.save(user);
            UserResponseDTO responseDTO = UserMapper.userToUserResponseDto(savedUser);

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Registration failed: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO, HttpSession session) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginDTO.getUsername());
        if(userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(userLoginDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDto(user);
        session.setAttribute("user", userResponseDTO);
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> checkLogin(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null) {
            return ResponseEntity.ok(Map.of(
                    "authenticated", false,
                    "message", "Not authenticated"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "user", userResponseDTO,
                "role", userResponseDTO.getRole() != null ? userResponseDTO.getRole() : "USER"
        ));
    }

    public ResponseEntity<?> logout(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        session.removeAttribute("user");
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> dashboard(HttpSession session) {
        UserResponseDTO userResponseDTO = (UserResponseDTO) session.getAttribute("user");
        if (userResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userResponseDTO);
    }

    public ResponseEntity<?> uploadAvatar(HttpSession session, MultipartFile file) {
        UserResponseDTO userDTO = (UserResponseDTO) session.getAttribute("user");
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = userDTO.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (session.getAttribute("Avatar upload") != null) {
            User user = userOptional.get();
            UserMapper.deleteAvatar(user.getAvatar());
            user.setAvatar(DEFAULT_USER_IMAGE_URL);
            userRepository.save(user);

            return ResponseEntity.ok("Аватар удалён");
        }
        return ResponseEntity.ofNullable("Аватар не найден");
    }
}