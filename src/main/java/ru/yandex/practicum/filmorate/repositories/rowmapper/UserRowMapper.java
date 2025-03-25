package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.User;

@Repository
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = User.builder()
				.id(rs.getLong("person_id"))
				.email(rs.getString("email"))
				.login(rs.getString("login"))
				.name(rs.getString("name"))
				.birthday(rs.getDate("birthday").toLocalDate())
				.build();
		return user;
	}

}
