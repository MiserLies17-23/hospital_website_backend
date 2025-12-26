package com.hospital.hospital_website.repository;

import com.hospital.hospital_website.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA репозиторий для сущности News
 */
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Находит новость по заголовку
     *
     * @param title заголовок новости
     * @return Optional с новостью или пустой
     */
    Optional<News> findByTitle(String title);

    /**
     * Находит новость по id
     *
     * @param id id новости
     * @return Optional с новостью или пустой
     */
    Optional<News> findById(Long id);

    /**
     * Найти все новости
     *
     * @return список всех новостей
     */
    List<News> findAll();
}
