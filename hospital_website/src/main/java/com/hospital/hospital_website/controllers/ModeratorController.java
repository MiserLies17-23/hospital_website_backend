package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.services.ModeratorService;
import com.hospital.hospital_website.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-контроллер для модератора
 * Предоставляет расширенный доступ к данным новостей
 */
@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {

    /** Объект ModeratorService для управления логикой возможностей модератора */
    private ModeratorService moderatorService;

    /** Объект NewsService для получения всех новостей */
    private NewsService newsService;

    /**
     * Получить все новости
     *
     * @return ResponseEntity с данными всех новостей
     */
    @GetMapping("/news")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getAllNews() {
        List<NewsResponseDTO> allNews = newsService.getAllNews();
        return ResponseEntity.ok(allNews);
    }

    /**
     * Добавить новость
     *
     * @param newsRequestDTO данные новостй
     * @return ResponseEntity с данными добавленной новости
     */
    @PostMapping("/news/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> addNews(@RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO newsResponseDTO = moderatorService.addNews(newsRequestDTO);
        return ResponseEntity.ok(newsResponseDTO);
    }

    /**
     * Получить новость по id
     *
     * @param newsId id новости
     * @return ResponseEntity с данными новости
     */
    @GetMapping("/news/{newsId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getNewsById(@PathVariable Long newsId) {
        NewsResponseDTO newsResponseDTO = moderatorService.getNewsById(newsId);
        return ResponseEntity.ok(newsResponseDTO);
    }

    /**
     * Изменить новость
     *
     * @param newsId id новости для изменения
     * @param newsRequestDTO данные для изменения
     * @return ResponseEntity с данными обновлённой новости
     */
    @PostMapping("/news/{newsId}/edit")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> editNews(@PathVariable Long newsId, @RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO newsResponseDTO = moderatorService.editNews(newsId, newsRequestDTO);
        return ResponseEntity.ok(newsResponseDTO);
    }

    /**
     * Удалить новость по id
     *
     * @param newsId id новости
     * @return статус удаления
     */
    @DeleteMapping("/news/{newsId}/delete")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> deleteNews(@PathVariable Long newsId) {
        moderatorService.deleteNews(newsId);
        return ResponseEntity.ok("Новость успешно удалена!");
    }
}
