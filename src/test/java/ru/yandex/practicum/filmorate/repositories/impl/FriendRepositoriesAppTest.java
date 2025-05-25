package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.repositories.FriendRepository;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FriendRepository.class, FriendRowMapper.class})
public class FriendRepositoriesAppTest {
	private final FriendRepository rep;

	private Friend friend;

	@BeforeEach
	void setUp() {
		for (Long id = 1L; id < 3; ++id) {
			friend = new Friend();
			friend.setUserId(id);
			friend.setFriendId(id + 1);
			Friend actual = rep.save(friend);
			assertTrue(actual != null);
			assertTrue(actual.getPrimaryKey().equals(id));
		}
	}

	@Test
	void findAllTest() {
		List<Friend> actual = rep.findAll();
		assertTrue(!actual.isEmpty());
	}

	@Test
	void findByPrimaryKeyTest() {
		Long primaryKey = 1L;
		Optional<Friend> actual = rep.findByPrimaryKey(primaryKey);
		assertTrue(actual.isPresent());
	}

	@Test
	void findFriendTest() {
		Long id = 1L;
		Long otherId = 2L;
		Optional<Friend> actual = rep.findFriend(id, otherId);

		assertTrue(actual.isPresent());
	}

	@Test
	void findFriendsByIdTest() {
		Long id = 1L;
		List<Friend> actual = rep.findFriendsById(id);

		assertTrue(!actual.isEmpty());
	}

}
