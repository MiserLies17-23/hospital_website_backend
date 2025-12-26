package com.hospital.hospital_website.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO-запрос для новостей
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestDTO {

    /** Заголовок новости */
    private String title;

    /** Контент новости */
    private String content;
}
