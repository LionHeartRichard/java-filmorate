package ru.yandex.practicum.filmorate.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.filmorate.model.impl.User;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BaseOperationsTest {

	@Mock
	private JdbcTemplate jdbc;

	@InjectMocks
	private BaseOperations baseOperations; // original class

	@Test
	void addWhenUserAddDbThenReturnId() {
		User user = User.builder()
				.id(null)
				.email("gmail@com")
				.login("loginDolores")
				.name("Dolores")
				.birthday(LocalDate.now())
				.build();
		String nameTable = "person";
		String pk = "person_id";		
	}
}
