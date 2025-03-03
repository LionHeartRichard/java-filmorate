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
import ru.yandex.practicum.filmorate.validation.impl.NotNegativeValueValidator;

@Constraint(validatedBy = NotNegativeValueValidator.class)
@Target({FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETER})
@Retention(RUNTIME)
public @interface NotNegativeValue {
	String message() default "Продолжительность фильма не может быть отрицательным значением!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
