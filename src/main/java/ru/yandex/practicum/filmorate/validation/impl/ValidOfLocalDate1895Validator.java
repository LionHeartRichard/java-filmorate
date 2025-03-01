package ru.yandex.practicum.filmorate.validation.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import ru.yandex.practicum.filmorate.validation.ValidOfLocalDate1895;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidOfLocalDate1895Validator implements ConstraintValidator<ValidOfLocalDate1895, LocalDate> {

	private static final LocalDate THRESHOLD_DATE = LocalDate.of(1895, 12, 28);

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		if (date == null)
			return true;
		return date.isAfter(THRESHOLD_DATE);
	}

}
