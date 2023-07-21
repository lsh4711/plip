package com.server.global.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = OptionalDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalDateValid {
	String message() default "날짜 형식이 올바르지 않습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
