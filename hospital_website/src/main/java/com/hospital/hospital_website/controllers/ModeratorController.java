package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.services.ModeratorService;
import com.hospital.hospital_website.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {
    private ModeratorService moderatorService;
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<?> getAllNews() {
        List<NewsResponseDTO> allNews = newsService.getAllNews();
        return ResponseEntity.ok(allNews);
    }

    @PostMapping("/news/add")
    public ResponseEntity<?> addNews(@RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO newsResponseDTO = moderatorService.addNews(newsRequestDTO);
        return ResponseEntity.ok(newsResponseDTO);
    }

    @PostMapping("/news/{newsId}")
    public ResponseEntity<?> editNews(@PathVariable Long newsId, @RequestBody NewsRequestDTO newsRequestDTO) {
        NewsResponseDTO newsResponseDTO = moderatorService.editNews(newsId, newsRequestDTO);
        return ResponseEntity.ok(newsResponseDTO);
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable Long newsId) {
        moderatorService.deleteNews(newsId);
        return ResponseEntity.ok("Новость успешно удалена!");
    }
}
