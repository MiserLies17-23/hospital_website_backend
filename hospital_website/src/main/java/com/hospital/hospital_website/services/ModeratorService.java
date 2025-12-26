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

@Service
@AllArgsConstructor
public class ModeratorService {
    private final NewsRepository newsRepository;
    private final UtilsSecurity utilsSecurity;

    public NewsResponseDTO addNews(NewsRequestDTO newsRequestDTO) {
        User user = utilsSecurity.getCurrentUser();
        News news = NewsMapper.newsRequestDTOtoNews(newsRequestDTO, user);
        News savedNews = newsRepository.save(news);
        return NewsMapper.newsToNewsResponseDTO(savedNews);
    }

    public NewsResponseDTO editNews(Long id, NewsRequestDTO newsRequestDTO) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена!"));
        if (!Objects.equals(id, news.getId()))
            throw new EntityNotFoundException("Ошибка поиска новости...");
        if (!Objects.equals(news.getTitle(), newsRequestDTO.getTitle()))
            news.setTitle(newsRequestDTO.getTitle());
        if (!Objects.equals(news.getContent(), newsRequestDTO.getContent()))
            news.setContent(newsRequestDTO.getContent());
        news.setDate(LocalDate.now());
        News savedNews = newsRepository.save(news);
        return NewsMapper.newsToNewsResponseDTO(savedNews);
    }

    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Новость не найдена!"));
        newsRepository.delete(news);
    }

    public NewsResponseDTO getNewsById(Long newsId) {
        Optional<News> optionalNews = newsRepository.findById(newsId);
        if (optionalNews.isEmpty())
            throw new EntityNotFoundException("Новость не найдена");
        return NewsMapper.newsToNewsResponseDTO(optionalNews.get());
    }
}
