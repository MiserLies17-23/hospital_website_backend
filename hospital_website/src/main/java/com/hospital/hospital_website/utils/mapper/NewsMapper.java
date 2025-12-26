package com.hospital.hospital_website.utils.mapper;

import com.hospital.hospital_website.dto.request.NewsRequestDTO;
import com.hospital.hospital_website.dto.response.NewsResponseDTO;
import com.hospital.hospital_website.models.News;
import com.hospital.hospital_website.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный компонент для конвертации данных новостей News
 */
@Component
public abstract class NewsMapper {

    /**
     * Преобразует DTO-запрос NewsRequestDTO в сущность News
     *
     * @param newsRequestDTO DTO-запрос с данными новости
     * @param user пользователь автор новости
     * @return сущность News
     */
    public static News newsRequestDTOtoNews(NewsRequestDTO newsRequestDTO, User user) {
        News news = new News();
        news.setUser(user);
        news.setTitle(newsRequestDTO.getTitle());
        news.setContent(newsRequestDTO.getContent());
        news.setDate(LocalDate.now());
        return news;
    }

    /**
     * Преобразует сущность News в DTO-ответ NewsResponseDTO
     *
     * @param news сущность новости
     * @return DTO-ответ с данными новости
     */
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

    /**
     * Преобразует список новостей в список DTO
     *
     * @param newsList список новостей
     * @return список DTO с данными всех новостей
     */
    public static List<NewsResponseDTO> getDTOnewsList(List<News> newsList){
        List<NewsResponseDTO> newsDTOList = new ArrayList<>();
        for (News news : newsList) {
            newsDTOList.add(newsToNewsResponseDTO(news));
        }
        return newsDTOList;
    }
}
