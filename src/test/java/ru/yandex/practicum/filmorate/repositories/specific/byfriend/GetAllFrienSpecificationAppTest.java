package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GetAllFrienIdSpecification.class, FriendRowMapper.class})
public class GetAllFrienSpecificationAppTest {
	private final GetAllFrienIdSpecification friendSpecification;

	void getFriends() {
		Long userId = 1L;
		Integer offset = 0;
		Long[] params = {userId, offset.longValue()};

		Map<Long, String> friendsId = friendSpecification.specified(params, new HashMap<>());

		assertTrue(!friendsId.isEmpty());
	}
}
