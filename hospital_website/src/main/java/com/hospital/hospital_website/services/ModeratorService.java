package com.hospital.hospital_website.services;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.exception.EntityNotFoundException;
import com.hospital.hospital_website.models.News;
import com.hospital.hospital_website.repository.NewsRepository;
import com.hospital.hospital_website.utils.mapper.NewsMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModeratorService {
    private final NewsRepository newsRepository;

    public ResponseEntity<?> addNews(NewsRequestDTO newsRequestDTO) {
        News news = NewsMapper.newsRequestDTOtoNews(newsRequestDTO);
        newsRepository.save(news);
        return ResponseEntity.ok(NewsMapper.newsToNewsResponseDTO(news));
    }

    public ResponseEntity<?> editNews(Long id, NewsRequestDTO newsRequestDTO) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty())
            throw new EntityNotFoundException("Новость не найдена!");
        News news = newsOptional.get();
        if (!Objects.equals(id, news.getId()))
            throw new EntityNotFoundException("Ошибка поиска новости...");
        if (!Objects.equals(news.getTitle(), newsRequestDTO.getTitle()))
            news.setTitle(newsRequestDTO.getTitle());
        if (!Objects.equals(news.getContent(), newsRequestDTO.getContent()))
            news.setContent(newsRequestDTO.getContent());
        if (!Objects.equals(news.getDate().toString(), newsRequestDTO.getDate()))
            news.setDate(LocalDate.parse(newsRequestDTO.getDate()));
        newsRepository.save(news);
        return ResponseEntity.ok(NewsMapper.newsToNewsResponseDTO(news));
    }

    public ResponseEntity<?> deleteNews(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty())
            throw new EntityNotFoundException("Новость не найдена!");
        News news = newsOptional.get();
        newsRepository.delete(news);
        return ResponseEntity.ok("Новость успешно удалена!");
    }
}
