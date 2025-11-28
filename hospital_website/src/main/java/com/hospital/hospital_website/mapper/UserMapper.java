package com.hospital.hospital_website.mapper;

import com.hospital.hospital_website.dto.UserCreateDTO;
import com.hospital.hospital_website.dto.UserResponseDTO;
import com.hospital.hospital_website.models.User;

public class UserMapper {

    private static final String DEFAULT_ROLE = "USER";

    /*
    private static final String USER_IMAGE_PATH = "src/main/resources/static/images/userImages/";

    private static final String USER_IMAGE_URL = "http://localhost:8080/images/userImages/";

    private static final String DEFAULT_USER_IMAGE_URL = "http://localhost:8080/images/userImages/defaultUserImage.png";
    **/
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
        //user.setAvatar(DEFAULT_USER_IMAGE_URL);

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
                user.getRole()
                //user.getAvatar()
        );
    }
    /*
    public static String avatarProcessing(MultipartFile avatar, String username) throws IOException {
        if (avatar == null || avatar.isEmpty()) {
            return DEFAULT_USER_IMAGE_URL;
        }

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
    }

    public static void deleteAvatar(String avatarUrl) throws IOException {
        if (avatarUrl == null || avatarUrl.equals(DEFAULT_USER_IMAGE_URL)) {
            return;
        }

        String fileName = avatarUrl.replace(USER_IMAGE_URL, "");
        if (!fileName.isEmpty()) {
            Path filePath = Paths.get(USER_IMAGE_PATH, fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        }
    }**/
}