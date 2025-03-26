package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.impl.FilmGenre;

public class FilmGenreRowMapper implements RowMapper<FilmGenre> {

	@Override
	public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
		FilmGenre filmGenre = new FilmGenre(rs.getLong("film_id"), rs.getString("name"));
		return filmGenre;
	}

}
