package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.Friend;

@Repository
public class FriendRowMapper implements RowMapper<Friend> {

	@Override
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend row = Friend.builder()
				.userId(rs.getLong("person_id"))
				.friendId(rs.getLong("friend_id"))
				.build();
		return row;
	}

}
