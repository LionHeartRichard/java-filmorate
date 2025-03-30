package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

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
@Import({GetStatusFriendSpecification.class, FriendRowMapper.class})
public class GetStatusFriendSpecificationTest {
	private final GetStatusFriendSpecification sp;

	@Test
	void getFriendInDatabaseWhenRightIdThenReturnFriend() {
		Long[] params = {1L, 3L};

		Optional<Friend> friendOpt = sp.specified(params, Optional.empty());

		assertTrue(friendOpt.isPresent());
	}
}
