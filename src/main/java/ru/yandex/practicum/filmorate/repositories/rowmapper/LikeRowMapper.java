package ru.yandex.practicum.filmorate.repositories.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.impl.Like;

@Repository
public class LikeRowMapper implements RowMapper<Like> {

	@Override
	public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
		Like like = Like.builder().
				filmId(rs.getLong("film_id")).
				userId(rs.getLong("person_id")).
				build();
		return like;
	}

}
