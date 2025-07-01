package ru.yandex.practicum.filmorate.repositories;

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
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FriendRepository.class, FriendRowMapper.class})
public class FriendRepositoriesAppTest {

	private final FriendRepository rep;
	private Friend friend;
	private static Long userId = 0L, friendId = 3L;

	@BeforeEach
	void setUp() {
		friend = new Friend();
		userId = userId < 3 ? userId + 1 : userId - 1;
		friend.setUserId(userId);
		friendId = friendId < 3 ? friendId + 1 : friendId - 1;
		friend.setFriendId(friendId);
		rep.save(friend);
	}

}
