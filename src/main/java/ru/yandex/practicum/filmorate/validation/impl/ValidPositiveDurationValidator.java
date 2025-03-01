package ru.yandex.practicum.filmorate.validation.impl;

import jakarta.validation.constraintvalidation.ValidationTarget;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Duration;
import ru.yandex.practicum.filmorate.validation.ValidPositiveDuration;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidPositiveDurationValidator implements ConstraintValidator<ValidPositiveDuration, Duration> {

	@Override
	public boolean isValid(Duration duration, ConstraintValidatorContext context) {
		if (duration == null)
			return true;
		return duration.getSeconds() > 0;
	}

}
