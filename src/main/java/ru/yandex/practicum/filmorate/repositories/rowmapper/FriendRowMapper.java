package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.impl.Friend;

public class FriendRowMapper implements RowMapper<Friend> {

	@Override
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend friend = new Friend(rs.getLong("person_id"), rs.getLong("friend_id"), rs.getString("status_name"));
		return friend;
	}

}
