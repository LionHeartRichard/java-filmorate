package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.Rating;

@Repository
public class RatingRowMapper implements RowMapper<Rating> {

	@Override
	public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
		Rating rating = Rating.builder().name(rs.getString("name")).build();
		return rating;
	}

}
