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

/**
 * Вспомогательный компонент для конвертации пользователей User
 */
@Component
public abstract class UserMapper {

    /** Роль пользователя по умолчанию */
    private static final UserRole DEFAULT_ROLE = UserRole.USER;

    /** Абсолютный путь к локальной директории с аватарами пользователей */
    private static final String USER_IMAGE_PATH = "C:/users/THUNDEROBOT/IdeaProjects/hospital_website/hospital_website/src/main/resources/static/images/userImages";

    /** Путь к удалённой директории с аватарами пользователей */
    private static final String USER_IMAGE_URL = "http://localhost:8080/images/userImages/";

    /** Абсолютный путь к дефолтному аватару пользователей */
    private static final String DEFAULT_USER_IMAGE_URL = "http://localhost:8080/images/userImages/defaultUserImage.jpg";

    /**
     * Преобразует DTO-запрос UserCreateDTO в сущность User
     *
     * @param userCreateDTO запрос с DTO-данными пользователя для регистрации
     * @return сущность User
     */
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

    /**
     * Преобразует сущность User в DTO-ответ UserResponseDTO
     *
     * @param user пользователь
     * @return DTO-ответ с данными пользователя
     */
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

    /**
     * Загружает аватар
     *
     * @param avatar аватар пользователя
     * @param username имя пользователя
     * @return url изображения
     */
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

            // Убедитесь, что имя файла уникально и безопасно
            String cleanUsername = username.replaceAll("[^a-zA-Z0-9]", "_");
            String fileName = cleanUsername + "_avatar_" + System.currentTimeMillis() + fileExtension;
            Path filePath = directoryPath.resolve(fileName);

            // Сохраняем файл
            avatar.transferTo(filePath.toFile());

            return USER_IMAGE_URL + fileName;
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении аватара: " + e.getMessage());
            return DEFAULT_USER_IMAGE_URL;
        }
    }

    /**
     * Удаляет аватар
     *
     * @param avatarUrl url дефолтного аватара
     */
    public static void deleteAvatar(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals(DEFAULT_USER_IMAGE_URL))
            return;

        try {
            // Проверяем, что это не дефолтный аватар
            if (avatarUrl.contains("defaultUserImage")) {
                return;
            }

            // Извлекаем имя файла из URL
            String fileName;
            if (avatarUrl.contains(USER_IMAGE_URL)) {
                // Если URL содержит базовый путь, извлекаем имя файла
                fileName = avatarUrl.replace(USER_IMAGE_URL, "");
            } else if (avatarUrl.contains("/userImages/")) {
                // Ищем часть после /userImages/
                int index = avatarUrl.indexOf("/userImages/") + "/userImages/".length();
                fileName = avatarUrl.substring(index);
            } else if (avatarUrl.contains("/images/userImages/")) {
                // Ищем часть после /images/userImages/
                int index = avatarUrl.indexOf("/images/userImages/") + "/images/userImages/".length();
                fileName = avatarUrl.substring(index);
            } else {
                // Если URL не содержит ожидаемых путей, берем последнюю часть после /
                fileName = avatarUrl.substring(avatarUrl.lastIndexOf('/') + 1);
            }

            // Убираем query параметры (например, ?t=123456)
            if (fileName.contains("?")) {
                fileName = fileName.substring(0, fileName.indexOf("?"));
            }

            if (!fileName.isEmpty()) {
                Path filePath = Paths.get(USER_IMAGE_PATH, fileName);
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    System.out.println("Файл удален: " + filePath);
                } else {
                    System.err.println("Файл не найден: " + filePath);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при удалении аватара: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка при удалении аватара: " + e.getMessage());
        }
    }

    /**
     * Преобразует сущность User в DTO-ответ AdminUserResponseDTO
     *
     * @param user пользователь
     * @return AdminUserResponseDTO с данными пользователя, доступных администратору
     */
    public static AdminUserResponseDTO userToAdminUserResponseDto(User user) {
        if (user == null)
            return null;
        return new AdminUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().toString(),
                user.getAvatar()
        );
    }

}