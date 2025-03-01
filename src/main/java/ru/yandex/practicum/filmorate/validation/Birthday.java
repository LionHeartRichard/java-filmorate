package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = BirthdayValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Birthday {
	String message() default "День рождение не может быть в будущем времени!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] pauload() default {};
}
