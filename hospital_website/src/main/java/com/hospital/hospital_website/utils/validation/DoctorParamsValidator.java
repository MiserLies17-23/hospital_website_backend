package com.hospital.hospital_website.utils.validation;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.exception.ValidateException;

public abstract class DoctorParamsValidator {

    private static final String NAME_REGEX = "([А-ЯЁ][а-яё]+\\s){2}[А-ЯЁ][а-яё]";
    private static final String PHONE_REGEX = "(\\+7\\s([(]\\d{4}[)])\\s\\d{3}-\\d{2}-\\d{2})";
    private static final String SPECIALIZATION_REGEX = "[а-яА-Я][а-яА-ЯЕё]{3,}";

    public static void doctornameValidate(String doctorname) {
        if (!doctorname.matches(NAME_REGEX))
            throw new ValidateException("Введите ФИО врача!");
    }

    public static void doctorphoneValidate(String doctorphone) {
        if (!doctorphone.matches(PHONE_REGEX))
            throw new ValidateException("Неправильный формат телефона! Пример: +7 (999) 999-99-99");
    }

    public static void doctorSpecializationValidate(String specialization) {
        if (!specialization.matches(SPECIALIZATION_REGEX))
            throw new ValidateException("Специальность должна быть на кириллице!");
    }

    public static void doctorParamValidate(DoctorRequestDTO doctorRequestDTO) {
        doctornameValidate(doctorRequestDTO.getName());
        doctorphoneValidate(doctorRequestDTO.getPhone());
        doctorSpecializationValidate(doctorRequestDTO.getSpecialization());
    }
}
