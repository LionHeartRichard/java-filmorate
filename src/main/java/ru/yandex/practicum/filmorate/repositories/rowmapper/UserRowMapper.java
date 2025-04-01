package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.User;

@Repository
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		Array arr = rs.getArray("friends");
		Object[] swap = arr == null ? null : (Object[]) arr.getArray();
		Set<Long> friends = swap == null ? null
				: Arrays.stream(swap).map(obj -> (Long) obj).collect(Collectors.toSet());
		User row = User
				.builder()
				.id(rs.getLong("person_id"))
				.email(rs.getString("email"))
				.login(rs.getString("login"))
				.name(rs.getString("name"))
				.birthday(rs.getDate("birthday").toLocalDate())
				.firends(friends)
				.build();
		return row;
	}

}
