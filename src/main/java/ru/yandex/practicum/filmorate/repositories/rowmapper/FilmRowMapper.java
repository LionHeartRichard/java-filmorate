package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class FilmRowMapper implements RowMapper<Film> {

	@Override
	public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
		Film row = new Film(
				rs.getLong("film_id"),
				rs.getString("name"),
				rs.getString("description"),
				rs.getDate("release_date").toLocalDate(),
				rs.getInt("duration"),
				rs.getLong("mpa_id")
				);
		return row;
	}

}
