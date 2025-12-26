package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности Doctor
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Находит врача по id
     *
     * @param id id врача
     * @return Optional с данными врача или пустой
     */
    Optional<Doctor> findById(Long id);

    /**
     * Находит всех врачей
     *
     * @return список всех врачей
     */
    List<Doctor> findAll();

    /**
     * Находит врача по ФИО
     *
     * @param name ФИО врача
     * @return Optional с данными врача или пустой
     */
    Optional<Doctor> findByName(String name);

    /**
     * Находит всех врачей по заданной специальности
     *
     * @param specialization специальность
     * @return список врачей соответствующей специальности
     */
    List<Doctor> findBySpecialization(String specialization);
}
