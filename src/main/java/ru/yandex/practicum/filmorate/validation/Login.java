package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import ru.yandex.practicum.filmorate.validation.impl.LoginValidator;

@Constraint(validatedBy = LoginValidator.class)
@Target({FIELD, METHOD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface Login {
	String message() default "Login не может быть пустым и не должен содержать пробелы!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
