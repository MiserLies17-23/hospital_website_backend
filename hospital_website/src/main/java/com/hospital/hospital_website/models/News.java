package com.hospital.hospital_website.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Сущность для представления новостей в базе данных
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="news")
public class News {

    /** Уникальный id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Объект User для хранения имени пользователя в базе данных */
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    /** Заголовок новости */
    @Column(nullable = false)
    private String title;

    /** Контент новости */
    @Column(columnDefinition = "TEXT")
    private String content;

    /** Отформатированная дата публикации новости */
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
