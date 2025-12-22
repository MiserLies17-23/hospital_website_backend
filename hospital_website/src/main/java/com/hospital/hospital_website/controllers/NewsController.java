package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<?> getAllNews() {
        List<NewsResponseDTO> allNews = newsService.getAllNews();
        return ResponseEntity.ok(allNews);
    }

}
