package com.hospital.hospital_website.exception;

public class AppointmentFoundException extends EntityFoundException {

    public AppointmentFoundException() {
        super("Запись не найдена!");
    }

    public AppointmentFoundException(Long appointmentId) {
        super("Запись", appointmentId);
    }
}
