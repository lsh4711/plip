package com.server.global.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValid, LocalDateTime> {

    @Override
    public void initialize(DateValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            LocalDateTime.from(value);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
