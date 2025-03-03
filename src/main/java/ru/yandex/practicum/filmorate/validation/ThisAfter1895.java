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
import ru.yandex.practicum.filmorate.validation.impl.ThisAfter1895Validator;

@Constraint(validatedBy = ThisAfter1895Validator.class)
@Target({FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETER})
@Retention(RUNTIME)
public @interface ThisAfter1895 {

	String message() default "Дата не может быть раньше 28 декабря 1895 года!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
