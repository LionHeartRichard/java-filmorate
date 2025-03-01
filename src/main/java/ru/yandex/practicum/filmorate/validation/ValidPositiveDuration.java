package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.impl.ValidPositiveDurationValidator;

@Constraint(validatedBy = ValidPositiveDurationValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPositiveDuration {
	String message() default "Продолжительность должна быть положительной величиной";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
