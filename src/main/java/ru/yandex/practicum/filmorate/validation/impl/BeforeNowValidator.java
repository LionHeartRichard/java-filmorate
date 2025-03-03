package ru.yandex.practicum.filmorate.validation.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import ru.yandex.practicum.filmorate.validation.BeforeNow;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class BeforeNowValidator implements ConstraintValidator<BeforeNow, LocalDate> {

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		return value.isBefore(LocalDate.now());
	}

}
