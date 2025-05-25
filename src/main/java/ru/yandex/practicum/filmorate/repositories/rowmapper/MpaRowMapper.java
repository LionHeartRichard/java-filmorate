package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Mpa;

@Repository
public class MpaRowMapper implements RowMapper<Mpa> {

	@Override
	public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Mpa row = new Mpa(
				rs.getLong("mpa_id"),
				rs.getLong("film_id"),
				rs.getString("name")
				);
		return row;
	}

}
