package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repositories.UserRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserRepository.class, UserRowMapper.class})
public class UserRepositoryAppTest {

	private final UserRepository rep;
	private User user;
	private static int postfix = 0;

	@BeforeEach
	void setUp() {
		user = new User();
		++postfix;
		user.setEmail("addgmailDolores@com" + postfix);
		user.setLogin("add-Login-Dolores" + postfix);
		user.setName("addDolores-Name" + postfix);
		user.setBirthday(LocalDate.now());
		user = rep.save(user);
	}

	@Test
	void findAllTest() {
		List<User> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByIdTest() {
		Optional<User> actualOpt = rep.findById(user.getId());
		assertTrue(actualOpt.isPresent());
	}

	@Test
	void findByEmailTest() {
		Optional<User> actualOpt = rep.findByEmail(user.getEmail());
		assertEquals(user, actualOpt.get());
	}

	@Test
	void findByNameTest() {
		String name = "%lor%";
		List<User> actual = rep.findByName(name);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByLoginTest() {
		String loginUser = "%lor%";
		List<User> actual = rep.findByLogin(loginUser);
		assertTrue(!actual.isEmpty());
	}

	@Test
	void updateTest() {
		String updateName = "UP-name-for-Dolores";
		user.setName(updateName);
		User actual = rep.update(user);
		assertEquals(updateName, actual.getName());
	}

	@Test
	void deleteUserByIdTest() {
		user.setEmail("updateem@yandex.ru");
		User expected = rep.save(user);
		Long id = expected.getId();
		assertTrue(rep.deleteUserById(id));
	}

	@Test
	void deleteUserByEmailTest() {
		String email = "deleteem@yandex.ru";
		user.setEmail(email);
		rep.save(user);
		assertTrue(rep.deleteUserByEmail(email));
	}

}
