package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.FilmGenre;

@Repository
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {

	@Override
	public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
		FilmGenre row = new FilmGenre(
				rs.getLong("film_genre_id"), 
				rs.getLong("genre_id"), 
				rs.getLong("film_id")
				);
		return row;
	}

}
