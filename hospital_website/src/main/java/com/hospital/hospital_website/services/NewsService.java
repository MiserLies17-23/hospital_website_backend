package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.models.News;
import com.hospital.hospital_website.repository.NewsRepository;
import com.hospital.hospital_website.utils.mapper.NewsMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис, реализующий работу с новостями
 */
@Service
@AllArgsConstructor
public class NewsService {

    /** Объект NewsRepository для получения новостей из базы данных */
    private final NewsRepository newsRepository;

    /**
     * Возвращает все новости
     *
     * @return список DTO с данными всех новостей
     */
    public List<NewsResponseDTO> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return NewsMapper.getDTOnewsList(newsList);
    }
}
