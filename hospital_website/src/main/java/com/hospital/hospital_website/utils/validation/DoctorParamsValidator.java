package com.hospital.hospital_website.utils.validation;

import com.hospital.hospital_website.dto.request.DoctorRequestDTO;
import com.hospital.hospital_website.exception.ValidateException;

/**
 * Вспомогательный компонент для валидации данных врача
 */
public abstract class DoctorParamsValidator {

    /** Шаблон ФИО врача */
    private static final String NAME_REGEX = "^[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)?\\s[А-ЯЁ][а-яё]+\\s[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)?$";

    /** Шаблон для телефона врача */
    private static final String PHONE_REGEX = "[+]7\\w{10}";

    /** Шаблон для специальности врача */
    private static final String SPECIALIZATION_REGEX = "[а-яА-Я][а-яА-ЯЕё]{3,}";

    /**
     * Проверяет ФИО врача на соответствие шаблону
     *
     * @param doctorname ФИО врача для проверки
     */
    public static void doctornameValidate(String doctorname) {
        if (!doctorname.matches((NAME_REGEX)))
            throw new ValidateException("Введите ФИО врача!");
    }

    /**
     * Проверяет номер телефона врача на соответствие шаблону
     *
     * @param doctorphone телефон врача для проверки
     */
    public static void doctorphoneValidate(String doctorphone) {
        if (!doctorphone.matches(PHONE_REGEX))
            throw new ValidateException("Неправильный формат телефона! Пример: +7 (999) 999-99-99");
    }

    /**
     * Проверяет специальность врача на соответствие шаблону
     *
     * @param specialization специальность врача для проверки
     */
    public static void doctorSpecializationValidate(String specialization) {
        if (!specialization.matches(SPECIALIZATION_REGEX))
            throw new ValidateException("Специальность должна быть на кириллице!");
    }

    /**
     * Проверяет все данные врача на допустимость
     *
     * @param doctorRequestDTO данные врача
     */
    public static void doctorParamValidate(DoctorRequestDTO doctorRequestDTO) {
        doctornameValidate(doctorRequestDTO.getName());
        doctorphoneValidate(doctorRequestDTO.getPhone());
        doctorSpecializationValidate(doctorRequestDTO.getSpecialization());
    }
}
