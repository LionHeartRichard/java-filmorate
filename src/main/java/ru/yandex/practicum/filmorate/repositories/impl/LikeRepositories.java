package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Like;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.LikeRowMapper;

@Slf4j
@Repository
public class LikeRepositories extends BaseRepo<Like> implements Repositories<Like> {

	@Autowired
	public LikeRepositories(JdbcTemplate jdbc, LikeRowMapper rowMapper) {
		super(jdbc, rowMapper, "film_person", "film_id");
	}

	@Override
	public Optional<Long> add(Like like) {
		log.trace("Like: {}", Optional.ofNullable(like.toString()).orElse("null"));
		String queryInsert = "INSERT INTO film_person VALUES(?, ?)";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, like.getFilmId());
				ps.setLong(2, like.getUserId());
			}
		};
		return super.add(queryInsert, pss);
	}

	public Optional<Integer> remove(Long filmId, Long userId) {
		log.trace("remove filmId: {}, userId: {}", filmId, userId);
		String queryDelete = "DELETE FROM film_person WHERE film_id = ? AND person_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, filmId);
				ps.setLong(2, userId);
			}
		};
		return super.update(queryDelete, pss);
	}

	@Deprecated
	@Override
	public Optional<Integer> update(Like like) {
		return Optional.empty();
	}

	public Map<Long, Integer> getTopFilms(int limit) {
		String queryCount = String.format(
				"SELECT film_id, COUNT(*) AS like_count FROM film_person GROUP BY film_id ORDER BY like_count DESC LIMIT %d",
				limit);
		return jdbc.query(queryCount, rs -> {
			Map<Long, Integer> ans = new HashMap<>();
			while (rs.next()) {
				Long filmId = rs.getLong("film_id");
				Integer count = rs.getInt("like_count");
				ans.put(filmId, count);
			}
			return ans;
		});
	}
	
}
