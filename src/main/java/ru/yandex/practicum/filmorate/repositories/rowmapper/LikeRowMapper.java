package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Like;

@Repository
public class LikeRowMapper implements RowMapper<Like> {

	@Override
	public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
		Like row = new Like(
				rs.getLong("film_person_id"),
				rs.getLong("film_id"),
				rs.getLong("person_id")
				);
		return row;
	}

}
