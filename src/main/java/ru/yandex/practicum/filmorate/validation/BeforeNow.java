package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ru.yandex.practicum.filmorate.validation.impl.BeforeNowValidator;

@Constraint(validatedBy = BeforeNowValidator.class)
@Target({FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETER})
@Retention(RUNTIME)
public @interface BeforeNow {
	String message() default "День рождение не может быть позже настоящей даты!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
