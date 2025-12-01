package com.hospital.hospital_website.mapper;

import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserMapper {

    private static final String DEFAULT_ROLE = "USER";

    private static final String USER_IMAGE_PATH = "C:\\users\\THUNDEROBOT\\IdeaProjects\\hospital_website\\hospital_website\\src\\main\\resources\\images\\userImages";

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
                user.getRole(),
                user.getAvatar()
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

    public static void deleteAvatar(String avatarUrl, String username) {
        if (avatarUrl == null || avatarUrl.equals(DEFAULT_USER_IMAGE_URL)) {
            return;
        }

        try {
            String fileName = avatarUrl.replace(USER_IMAGE_URL, "");
            if (!fileName.isEmpty()) {
                Path filePath = Paths.get(USER_IMAGE_PATH, fileName);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}