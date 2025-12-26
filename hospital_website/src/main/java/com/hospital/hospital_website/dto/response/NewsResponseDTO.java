package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-ответ данных новости
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDTO {

    /** Уникальный id новости */
    private long id;

    /** Автор новости */
    private String author;

    /** Заголовок новости */
    private String title;

    /** Контент новости */
    private String content;

    /** Дата публикации новости */
    private String date;
}
