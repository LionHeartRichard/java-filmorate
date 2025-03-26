package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.impl.FilmUser;

public class FilmUserRowMapper implements RowMapper<FilmUser> {

	@Override
	public FilmUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		FilmUser filmUser = new FilmUser(rs.getLong("film_id"), rs.getLong("person_id"));
		return filmUser;
	}

}
