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
import ru.yandex.practicum.filmorate.dto.UserDto.Request.Create;

@Slf4j
public class UserDtoRequestCreateTest {
	private static Validator validator;
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testPositive() {
		final Create user = new Create("dolores@yandex.ru", "dolore", "Dolores", LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<Create>> violations = validator.validate(user);

		violations.forEach(violation -> log.error(violation.getMessage()));
		assertTrue(violations.isEmpty());
	}

	@Test
	public void testNegativeEmail() {
		final Create user = new Create("doloresyandex.ru", "dolore", "Dolores", LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<Create>> violations = validator.validate(user);

		for (ConstraintViolation<Create> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeLogin() {
		final Create user = new Create("dolores@yandex.ru", "dolore login", "Dolores", LocalDate.of(2000, 8, 20));
		Set<ConstraintViolation<Create>> violations = validator.validate(user);

		for (ConstraintViolation<Create> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}

	@Test
	public void testNegativeBirthDay() {
		final Create user = new Create("dolores@yandex.ru", "dolore", "Dolores", LocalDate.of(2446, 8, 20));
		Set<ConstraintViolation<Create>> violations = validator.validate(user);

		for (ConstraintViolation<Create> violation : violations) {
			log.error(violation.getMessage());
		}

		assertTrue(!violations.isEmpty());
	}
}
