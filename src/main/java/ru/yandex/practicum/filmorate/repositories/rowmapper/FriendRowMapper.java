package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Friend;

@Repository
public class FriendRowMapper implements RowMapper<Friend> {

	@Override
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend row = new Friend(
				rs.getLong("friend_id"), 
				rs.getLong("id"), 
				rs.getLong("other_id")
				);
		return row;
	}

}
