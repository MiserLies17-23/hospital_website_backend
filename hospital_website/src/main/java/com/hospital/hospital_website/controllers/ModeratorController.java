package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.services.ModeratorService;
import com.hospital.hospital_website.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
@AllArgsConstructor
public class ModeratorController {
    private ModeratorService moderatorService;
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<?> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping("/news/add")
    public ResponseEntity<?> addNews(@RequestBody NewsRequestDTO newsRequestDTO) {
        return moderatorService.addNews(newsRequestDTO);
    }

    @PostMapping("/news/{newsId}")
    public ResponseEntity<?> editNews(@PathVariable Long newsId, @RequestBody NewsRequestDTO newsRequestDTO) {
        return moderatorService.editNews(newsId, newsRequestDTO);
    }

    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable Long newsId) {
        return moderatorService.deleteNews(newsId);
    }
}
