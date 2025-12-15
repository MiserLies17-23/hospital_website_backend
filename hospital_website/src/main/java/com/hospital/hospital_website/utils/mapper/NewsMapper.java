package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.models.News;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper {

    public static News newsRequestDTOtoNews(NewsRequestDTO newsRequestDTO) {
        News news = new News();
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDate(LocalDate.parse(newsRequestDTO.getDate()));
        return news;
    }

    public static NewsResponseDTO newsToNewsResponseDTO(News news){
        if (news == null)
            return null;
        return new NewsResponseDTO(
                news.getId(),
                news.getTitle(),
                news.getTitle(),
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
