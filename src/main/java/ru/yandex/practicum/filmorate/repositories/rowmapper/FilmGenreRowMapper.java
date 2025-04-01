package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.FilmGenre;

@Repository
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {

	@Override
	public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
		FilmGenre row = FilmGenre
				.builder()
				.genreId(rs.getLong("genre_id"))
				.filmId(rs.getLong("film_id"))
				.build();
		return row;
	}

}
