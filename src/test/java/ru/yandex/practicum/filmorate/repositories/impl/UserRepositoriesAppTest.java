package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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
@Import({UserRepositories.class, UserRowMapper.class})
public class UserRepositoriesAppTest {

	private final UserRepositories userRepo;

	private User user;

	@BeforeEach
	void setUp() {
		user = User.builder().id(null).email("addgmail@com").login("addLoginDolores").name("addDolores")
				.birthday(LocalDate.now()).build();
	}

	@Test
	void addWhenUserInsertDbReturnId() {
		Optional<Long> actual = userRepo.add(user);

		assertTrue(actual.isPresent());
	}

	@Test
	void removeWhenUserDeleteDbReturnInteger() {
		Optional<Integer> expected = Optional.ofNullable(1);
		user.setId(2L);

		Optional<Integer> actual = userRepo.remove(user.getId());

		assertEquals(expected, actual);
	}

	@Test
	void removeWhenUserNotDeleteDbReturnNull() {
		Optional<Integer> expected = Optional.ofNullable(null);
		user.setId(9000L);

		Optional<Integer> actual = userRepo.remove(user.getId());

		assertEquals(expected, actual);
	}

	@Test
	void updateWhenUserUpdateDbThenRetirnInteger() {
		Optional<Integer> expected = Optional.ofNullable(1);
		user.setId(1L);

		Optional<Integer> actual = userRepo.update(user);

		assertEquals(expected, actual);
	}

	@Test
	void updateFriendsWhenValidData() {
		Set<Long> friends = new HashSet<>();
		friends.add(2L);
		friends.add(3L);
		Optional<Integer> tmp = userRepo.updateFriends(friends, 1L);
		assertTrue(tmp.isPresent());

		User user = userRepo.getById(1L).get();
		Set<Long> actual = user.getFirends();

		friends.forEach(id -> assertTrue(actual.contains(id)));
	}
	
	@Test
	void getFriendsWhenNotFoundUserThenReturnNull() {
		Optional<User> actual = userRepo.getById(100000L);
		
		assertTrue(actual.isEmpty());
	}

}
