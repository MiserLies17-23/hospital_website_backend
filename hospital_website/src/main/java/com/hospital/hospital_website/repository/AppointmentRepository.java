package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findById(Long id);

    List<Appointment> findByUserId(Long id);

    List<Appointment> findAll();

    void deleteByUserId(Long id);

    void deleteByDoctorId(Long id);

    List<Appointment> findByDoctorId(Long id);
}
