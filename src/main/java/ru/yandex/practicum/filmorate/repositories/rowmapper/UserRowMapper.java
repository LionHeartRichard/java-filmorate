package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.User;

@Component
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getLong("person_id"), rs.getString("email"), rs.getString("login"),
				rs.getString("name"), rs.getDate("birthday").toLocalDate());
		return user;
	}

}
