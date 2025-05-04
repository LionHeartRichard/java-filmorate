package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.Mpa;

@Repository
public class MpaRowMapper implements RowMapper<Mpa> {

	@Override
	public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
		Mpa row = Mpa
				.builder()
				.mpaId(rs.getLong("mpa_id"))
				.filmId(rs.getLong("film_id"))
				.build();
		return row;
	}

}
