package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseRepository<T> {

	protected final JdbcTemplate jdbc;
	protected final RowMapper<T> mapper;

	protected Optional<T> findOne(String query, Object... params) {
		try {
			T ans = jdbc.queryForObject(query, mapper, params);
			return Optional.ofNullable(ans);
		} catch (EmptyResultDataAccessException ignored) {
			return Optional.empty();
		}
	}

	protected List<T> findMany(String query, Object... params) {
		return jdbc.query(query, mapper, params);
	}

}
