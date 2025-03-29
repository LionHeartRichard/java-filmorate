package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
@Import({TableFriendSpecification.class, FriendRowMapper.class})
public class TableFriendSpecificationAppTest {
	private final TableFriendSpecification tableSpec;

	@Test
	void getTableData() {
		List<Friend> friends = tableSpec.specified(0, new ArrayList<>());

		assertTrue(!friends.isEmpty());
	}
}
