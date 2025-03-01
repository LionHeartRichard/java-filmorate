package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.impl.ValidOfLocalDate1895Validator;

@Constraint(validatedBy = ValidOfLocalDate1895Validator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOfLocalDate1895 {

	String message() default "Дата не может быть раньше 28 декабря 1895 года!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
