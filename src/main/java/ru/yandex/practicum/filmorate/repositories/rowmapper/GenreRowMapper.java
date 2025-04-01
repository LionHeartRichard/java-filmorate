package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.yandex.practicum.filmorate.model.impl.Genre;

public class GenreRowMapper implements RowMapper<Genre>{

	@Override
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
		Genre row = Genre
				.builder()
				.genreId(rs.getLong("genre_id"))
				.name(rs.getString("name"))
				.build();
		return row;
	}

}
