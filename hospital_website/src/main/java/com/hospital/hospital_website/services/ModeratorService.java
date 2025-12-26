package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.News;
import com.hospital.hospital_website.models.User;
import com.hospital.hospital_website.repository.NewsRepository;
import com.hospital.hospital_website.utils.mapper.NewsMapper;
import com.hospital.hospital_website.utils.security.UtilsSecurity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис, реализующий логику операций модератора
 * Доступен только для роли MODERATOR
 */
@Service
@AllArgsConstructor
public class ModeratorService {

    /** Объект NewsRepository для получения новостей из базы данных */
    private final NewsRepository newsRepository;

    /** Объект UtilsSecurity для проверки авторизации пользователя */
    private final UtilsSecurity utilsSecurity;

    /**
     * Добавляет новость
     *
     * @param newsRequestDTO данные новости
     * @return DTO добавленной новости или ошибка
     */
    public NewsResponseDTO addNews(NewsRequestDTO newsRequestDTO) {
        User user = utilsSecurity.getCurrentUser();
        News news = NewsMapper.newsRequestDTOtoNews(newsRequestDTO, user);
        News savedNews = newsRepository.save(news);
        return NewsMapper.newsToNewsResponseDTO(savedNews);
    }

    /**
     * Изменяет новость по id
     *
     * @param newsId id новости
     * @param newsRequestDTO данные для изменения
     * @return DTO изменённой новости или ошибка
     */
    public NewsResponseDTO editNews(Long newsId, NewsRequestDTO newsRequestDTO) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена!"));
        if (!Objects.equals(newsId, news.getId()))
            throw new EntityNotFoundException("Ошибка поиска новости...");
        if (!Objects.equals(news.getTitle(), newsRequestDTO.getTitle()))
            news.setTitle(newsRequestDTO.getTitle());
        if (!Objects.equals(news.getContent(), newsRequestDTO.getContent()))
            news.setContent(newsRequestDTO.getContent());
        news.setDate(LocalDate.now());
        News savedNews = newsRepository.save(news);
        return NewsMapper.newsToNewsResponseDTO(savedNews);
    }

    /**
     * Удаляет новость по id
     *
     * @param id id новости для удаления
     */
    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена!"));
        newsRepository.delete(news);
    }

    /**
     * Возвращает новость по заданному id
     *
     * @param newsId id новости
     * @return DTO с данными новости
     */
    public NewsResponseDTO getNewsById(Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty())
            throw new EntityNotFoundException("Новость не найдена");
        return NewsMapper.newsToNewsResponseDTO(optionalNews.get());
    }
}
