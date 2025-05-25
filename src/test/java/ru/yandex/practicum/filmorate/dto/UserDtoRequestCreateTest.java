package ru.yandex.practicum.filmorate.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDtoRequestCreateTest {
	private static Validator validator;
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testPositive() {
		final UserDtoCreate user = new UserDtoCreate("dolores@yandex.ru", "dolore", "Dolores",
				LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<UserDtoCreate>> violations = validator.validate(user);

		violations.forEach(violation -> log.error(violation.getMessage()));
		assertTrue(violations.isEmpty());
	}

	@Test
	public void testNegativeEmail() {
		final UserDtoCreate user = new UserDtoCreate("doloresyandex.ru", "dolore", "Dolores",
				LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<UserDtoCreate>> violations = validator.validate(user);

		for (ConstraintViolation<UserDtoCreate> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeLogin() {
		final UserDtoCreate user = new UserDtoCreate("dolores@yandex.ru", "dolore login", "Dolores",
				LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<UserDtoCreate>> violations = validator.validate(user);

		for (ConstraintViolation<UserDtoCreate> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeBirthDay() {
		final UserDtoCreate user = new UserDtoCreate("dolores@yandex.ru", "dolore", "Dolores",
				LocalDate.of(2446, 8, 20));
		Set<ConstraintViolation<UserDtoCreate>> violations = validator.validate(user);

		for (ConstraintViolation<UserDtoCreate> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}
}
