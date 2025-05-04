package ru.yandex.practicum.filmorate.repositories.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FriendRepositories.class, FriendRowMapper.class})
public class FriendRepositoriesAppTest {
	private final FriendRepositories rep;

	private Friend friend;

	@BeforeEach
	void setUp() {
		friend = Friend.builder().userId(1L).friendId(2L).build();

		Optional<Long> actual = rep.add(friend);

		assertTrue(actual.isPresent());
	}

	@Test
	void removeRowWhenValidDataThenReturnNumbersRow() {
		Optional<Integer> actual = rep.removeRow(1L, 2L);

		assertTrue(actual.isPresent());
	}
}
