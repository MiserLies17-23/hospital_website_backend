package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.models.News;
import com.hospital.hospital_website.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class NewsMapper {


    public static News newsRequestDTOtoNews(NewsRequestDTO newsRequestDTO, User user) {
        News news = new News();
        news.setUser(user);
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDate(LocalDate.now());
        return news;
    }

    public static NewsResponseDTO newsToNewsResponseDTO(News news){
        if (news == null)
            return null;
        return new NewsResponseDTO(
                news.getId(),
                news.getUser().getUsername(),
                news.getTitle(),
                news.getContent(),
                news.getDate().toString()
        );
    }

    public static List<NewsResponseDTO> getDTOnewsList(List<News> newsList){
        List<NewsResponseDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            newsDTOList.add(newsToNewsResponseDTO(news));
        }
        return newsDTOList;
    }
}
