package ru.yandex.practicum.filmorate.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;

import ru.yandex.practicum.filmorate.validation.ValidLogin;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ValidLoginValidator implements ConstraintValidator<ValidLogin, String> {

	@Override
	public boolean isValid(String login, ConstraintValidatorContext context) {
		if (login == null)
			return false;
		if (login.isBlank())
			return false;
		if (login.split(" ").length > 1)
			return false;
		return true;
	}

}
