package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    Optional<News> findByTitle(String title);

    Optional<News> findById(Long id);

    List<News> findAll();
}
