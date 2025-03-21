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
public class FilmTest {

	private static Validator validator;
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testPositive() {

		final Film film = new Film(0L, "nisi eiusmod", "adipisicing", LocalDate.of(1967, 3, 25), 100, new HashSet<>());

		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		violations.forEach(violation -> log.error(violation.getMessage()));

		assertTrue(violations.isEmpty());
	}

	@Test
	public void testNegativeReleazeBefore1895() {

		final Film film = new Film(0L, "nisi eiusmod", "adipisicing", LocalDate.of(1894, 3, 25), 100, new HashSet<>());

		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		violations.forEach(violation -> log.error(violation.getMessage()));

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeDuration() {

		final Film film = new Film(0L, "nisi eiusmod", "adipisicing", LocalDate.of(2000, 3, 25), -100, new HashSet<>());

		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		violations.forEach(violation -> log.error(violation.getMessage()));

		assertTrue(!violations.isEmpty());
	}

}
