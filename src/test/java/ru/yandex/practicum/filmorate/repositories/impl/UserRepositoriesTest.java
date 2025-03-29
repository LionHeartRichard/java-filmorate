package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.PreparedStatementSetter;

import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.BaseOperations;
import ru.yandex.practicum.filmorate.repositories.specific.byuser.UserEmailSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.byuser.UserFindAllSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.byuser.UserIdSpecification;

@ExtendWith(MockitoExtension.class)
public class UserRepositoriesTest {

	@Mock
	private UserFindAllSpecification userFindAll;
	@Mock
	private UserEmailSpecification userFindByEmail;
	@Mock
	private UserIdSpecification userFindByid;
	@Mock
	private BaseOperations<User> operations;

	@Mock
	PreparedStatementSetter pss;

	@InjectMocks
	private UserRepositories userRepositories;

	private User user;
	private String nameTable;
	private String pk;

	@BeforeEach
	void setUp() {
		user = User.builder().id(null).email("gmail@com").login("loginDolores").name("Dolores")
				.birthday(LocalDate.now()).build();
		nameTable = "person";
		pk = "person_id";
	}

	@Test
	void addWhenUserInsertDbReturnId() {
		Optional<Long> expected = Optional.ofNullable(0L);
		Mockito.when(operations.add(user, nameTable, pk)).thenReturn(expected);

		Optional<Long> actual = userRepositories.add(user);

		assertEquals(expected, actual);
	}

	@Test
	void addWhenUserNotInsertDbReturnNull() {
		Optional<Long> expected = Optional.ofNullable(null);
		Mockito.when(operations.add(user, nameTable, pk)).thenReturn(expected);

		Optional<Long> actual = userRepositories.add(user);

		assertEquals(expected, actual);
	}

	@Test
	void removeWhenUserDeleteDbReturnInteger() {
		Optional<Integer> expected = Optional.ofNullable(1);
		user.setId(0L);
		Mockito.when(operations.remove(user.getId(), nameTable, pk)).thenReturn(expected);

		Optional<Integer> actual = userRepositories.remove(user.getId());

		assertEquals(expected, actual);
	}

	@Test
	void removeWhenUserNotDeleteDbReturnNull() {
		Optional<Integer> expected = Optional.ofNullable(null);
		user.setId(0L);

		Mockito.when(operations.remove(user.getId(), nameTable, pk)).thenReturn(expected);
		Optional<Integer> actual = userRepositories.remove(user.getId());

		assertEquals(expected, actual);
	}

//	@Test
//	void updateWhenUserUpdateDbThenRetirnInteger() {
//		Optional<Integer> expected = Optional.ofNullable(1);
//		user.setId(0L);
//
//		Mockito.when(operations.update("", pss)).thenReturn(expected);
//
//		Optional<Integer> actual = userRepositories.update(user);
//
//		assertEquals(expected, actual);
//	}
}
