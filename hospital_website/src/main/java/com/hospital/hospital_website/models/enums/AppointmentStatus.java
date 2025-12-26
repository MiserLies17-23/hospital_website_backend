package com.hospital.hospital_website.models.enums;

/**
 * Перечисление статусов записи к врачу
 */
public enum AppointmentStatus {

    /** Предстоящая запись */
    SCHEDULED,

    /** Завершённая запись */
    COMPLETED,

    /** Отменённая запись */
    CANCELLED
}
