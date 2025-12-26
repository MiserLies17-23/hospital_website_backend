package com.hospital.hospital_website.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность для представления врачей в базе данных
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="doctors")
public class Doctor {

    /** Уникальный id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ФИО врача */
    @Column(nullable = false)
    private String name;

    /** Специальность врача */
    @Column(nullable = false)
    private String specialization;

    /** Телефон врача */
    @Column(nullable = false)
    private String phone;

}
