package com.hospital.hospital_website.exception;

public class DoctorFoundException extends EntityFoundException {

    public DoctorFoundException(Long doctorId) {
        super("Доктор", doctorId);
    }
}
