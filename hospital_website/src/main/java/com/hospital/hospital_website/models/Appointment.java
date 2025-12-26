package com.hospital.hospital_website.models;

import com.hospital.hospital_website.models.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Сущность для представления записей в базе данных
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointments")
public class Appointment {

    /** Уникальный id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Объект User - пользователь, записанный к врачу */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Объект Doctor - врач, к которому оформлена запись */
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    /** Дата записи */
    @Column(nullable = false)
    private LocalDate date;

    /** Время запись */
    @Column(nullable = false)
    private LocalTime time;

    /** Симптомы болезни */
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    /** Статус записи */
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
}
