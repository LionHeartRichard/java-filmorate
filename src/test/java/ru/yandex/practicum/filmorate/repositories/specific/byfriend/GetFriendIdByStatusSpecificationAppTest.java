package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GetFriendIdByStatusSpecification.class, FriendRowMapper.class})
public class GetFriendIdByStatusSpecificationAppTest {

	private final GetFriendIdByStatusSpecification friendSpecification;

	@Test
	void getFriendsIdByStatus() {
		Long userId = 1L;
		String statusFriend = "Friends";
		Integer offset = 0;
		Object[] params = {userId, statusFriend, offset};

		Set<Long> frindsId = friendSpecification.specified(params, new HashSet<>());

		assertTrue(!frindsId.isEmpty());
	}
}
