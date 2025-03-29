package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserEmailSpecification.class, UserRowMapper.class})
public class UserEmailSpecificationAppTest {
	
	private final UserEmailSpecification userEmailSpec;

	@Test
	void getUserByEmail() {
		String email = "dolores@gmail.com";

		Optional<User> userOpt = userEmailSpec.specified(email, Optional.empty());

		assertTrue(userOpt.isPresent());
	}
}
