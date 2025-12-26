package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности Appointment
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Находит запись по уникальному id
     *
     * @param id id записи
     * @return Optional с новостью или пустой
     */
    Optional<Appointment> findById(Long id);

    /**
     * Находит все записи по id пользователя
     *
     * @param userId id пользователя
     * @return список всех записей пользователя с указанным id
     */
    List<Appointment> findByUserId(Long userId);

    /**
     * Находит все записи
     *
     * @return список всех записей, хранящихся в базе данных
     */
    List<Appointment> findAll();

    /**
     * Удаляет все записи по id пользователя
     *
     * @param userId id пользователя
     */
    void deleteByUserId(Long userId);

    /**
     * Удаляет все записи по id доктора
     *
     * @param doctorId id врача
     */
    void deleteByDoctorId(Long doctorId);

    /**
     * Находит все записи по id врача
     *
     * @param doctorId id врача
     * @return список записей к врачу с указанным id
     */
    List<Appointment> findByDoctorId(Long doctorId);
}
