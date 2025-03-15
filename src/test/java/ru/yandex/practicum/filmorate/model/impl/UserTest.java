package ru.yandex.practicum.filmorate.model.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTest {

	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private Validator validator = factory.getValidator();

	@Test
	public void testPositive() {
		final User user = new User(0L, "dolores@yandex.ru", "dolore", "Dolores", LocalDate.of(2000, 8, 20),
				new HashSet<>());
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		violations.forEach(violation -> log.error(violation.getMessage()));
		assertTrue(violations.isEmpty());
	}

	@Test
	public void testNegativeEmail() {
		final User user = new User(0L, "doloresyandex.ru", "dolore", "Dolores", LocalDate.of(2000, 8, 20),
				new HashSet<>());
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		for (ConstraintViolation<User> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeLogin() {
		final User user = new User(0L, "dolores@yandex.ru", "dolore login", "Dolores", LocalDate.of(2000, 8, 20),
				new HashSet<>());
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		for (ConstraintViolation<User> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeBirthDay() {
		final User user = new User(0L, "dolores@yandex.ru", "dolore", "Dolores", LocalDate.of(2446, 8, 20),
				new HashSet<>());
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		for (ConstraintViolation<User> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}
}
