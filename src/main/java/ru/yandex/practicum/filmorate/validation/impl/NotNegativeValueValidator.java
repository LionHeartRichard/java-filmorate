package ru.yandex.practicum.filmorate.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import ru.yandex.practicum.filmorate.validation.NotNegativeValue;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class NotNegativeValueValidator implements ConstraintValidator<NotNegativeValue, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		return value > 0;
	}

}
