package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.UserCreateDTO;
import com.hospital.hospital_website.dto.response.AdminUserResponseDTO;
import com.hospital.hospital_website.dto.response.UserResponseDTO;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.models.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public abstract class UserMapper {


    private static final UserRole DEFAULT_ROLE = UserRole.USER;

    private static final String USER_IMAGE_PATH = "C:\\users\\THUNDEROBOT\\IdeaProjects\\hospital_website\\hospital_website\\src\\main\\resources\\static\\images\\userImages";

    private static final String USER_IMAGE_URL = "http://localhost:8080/images/userImages/";

    private static final String DEFAULT_USER_IMAGE_URL = "http://localhost:8080/images/userImages/defaultUserImage.jpg";

    public static User userCrateDtoToUser(UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) {
            return null;
        }

        if (userCreateDTO.getUsername() == null || userCreateDTO.getPassword() == null) {
            throw new IllegalArgumentException("Username and password are required");
        }

        User user = new User();
        user.setUsername(userCreateDTO.getUsername().trim());
        user.setPassword(userCreateDTO.getPassword());
        user.setEmail(userCreateDTO.getEmail() != null ? userCreateDTO.getEmail().trim() : null);
        user.setRole(DEFAULT_ROLE);
        user.setAvatar(DEFAULT_USER_IMAGE_URL);

        return user;
    }

    public static UserResponseDTO userToUserResponseDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString(),
                user.getAvatar(),
                user.getVisitsCount()
        );
    }

    public static String avatarProcessing(MultipartFile avatar, String username) {
        if (avatar == null || avatar.isEmpty()) {
            return DEFAULT_USER_IMAGE_URL;
        }

        try {
            Path directoryPath = Paths.get(USER_IMAGE_PATH);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String originalFileName = avatar.getOriginalFilename();
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            String fileName = username + "_avatar" + fileExtension;
            Path filePath = directoryPath.resolve(fileName);

            avatar.transferTo(filePath.toFile());

            return USER_IMAGE_URL + fileName;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    public static void deleteAvatar(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals(DEFAULT_USER_IMAGE_URL))
            return;

        try {
            String fileName = avatarUrl.replace(USER_IMAGE_URL, "");
            if (!fileName.isEmpty()) {
                Path filePath = Paths.get(USER_IMAGE_PATH, fileName);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public static AdminUserResponseDTO userToAdminUserResponseDto(User user) {
        if (user == null)
            return null;
        return new AdminUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

}