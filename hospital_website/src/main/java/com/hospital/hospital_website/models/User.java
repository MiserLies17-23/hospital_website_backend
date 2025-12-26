package com.hospital.hospital_website.models;

import com.hospital.hospital_website.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность для представления пользователей в базе данных
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    /** Уникальный id пользователя */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Уникальное имя пользователя */
    @Column(unique = true, nullable = false)
    private String username;

    /** Пароль пользователя */
    @Column(nullable = false)
    private String password;

    /** Email пользователя */
    @Column (nullable = false)
    private String email;

    /** Роль пользователя */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    /** Url аватара пользователя */
    @Column
    private String avatar;

    /** Количество посещений системы пользователем */
    @Column(nullable = false)
    private int visitsCount;
}
