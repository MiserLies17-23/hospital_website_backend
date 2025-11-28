package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
