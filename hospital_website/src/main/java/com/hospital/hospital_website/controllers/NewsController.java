package com.hospital.hospital_website.controllers;

import com.hospital.hospital_website.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    @GetMapping("/")
    public ResponseEntity<?> getAllNews() {
        return newsService.getAllNews();
    }

}
