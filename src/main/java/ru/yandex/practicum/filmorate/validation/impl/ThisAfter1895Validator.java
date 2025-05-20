package ru.yandex.practicum.filmorate.validation.impl;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import ru.yandex.practicum.filmorate.util.GetConstants;
import ru.yandex.practicum.filmorate.validation.ThisAfter1895;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class ThisAfter1895Validator implements ConstraintValidator<ThisAfter1895, LocalDate> {

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		if (date == null)
			return true;
		return date.isAfter(GetConstants.THRESHOLD_DATE);
	}

}
