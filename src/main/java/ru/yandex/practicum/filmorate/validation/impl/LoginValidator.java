package ru.yandex.practicum.filmorate.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import ru.yandex.practicum.filmorate.validation.Login;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class LoginValidator implements ConstraintValidator<Login, String> {

	@Override
	public boolean isValid(String login, ConstraintValidatorContext context) {
		if (login == null || login.isEmpty())
			return false;
		return (login.indexOf(' ') == -1);
	}

}
