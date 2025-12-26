package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени
     *
     * @param username имя пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<User> findByUsername(String username);

    /**
     * Находит пользователя по id
     *
     * @param id пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<User> findById(Long id);

    /**
     * Находит пользователя по email
     *
     * @param email email пользователя
     * @return Optional с пользователем или пустой
     */
    Optional<User> findByEmail(String email);
}