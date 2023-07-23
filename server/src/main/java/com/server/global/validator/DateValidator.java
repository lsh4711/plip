package com.server.global.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValid, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            LocalDate.from(value);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
