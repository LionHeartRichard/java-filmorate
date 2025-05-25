package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Genre;

@Repository
public class GenreRowMapper implements RowMapper<Genre>{

	@Override
	public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
		Genre row = new Genre(
				rs.getLong("genre_id"),
				rs.getString("name")
				);
		return row;
	}

}
