package com.server.global.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OptionalDateValidator implements ConstraintValidator<OptionalDateValid, LocalDate> {

    @Override
    public void initialize(OptionalDateValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
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
