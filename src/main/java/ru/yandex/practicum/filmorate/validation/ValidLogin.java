package ru.yandex.practicum.filmorate.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.impl.ValidLoginValidator;

@Constraint(validatedBy = ValidLoginValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLogin {
	String message() default "Логин не может быть пустым или содержать пробелы!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] pyload() default {};

}
