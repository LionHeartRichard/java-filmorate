package ru.yandex.practicum.filmorate.validation.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import ru.yandex.practicum.filmorate.validation.Birthday;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class BirthdayValidator implements ConstraintValidator<Birthday, LocalDate> {

	@Override
	public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
		if (birthday == null)
			return true;
		if (birthday.isBefore(LocalDate.now()))
			return true;
		return false;
	}

}
