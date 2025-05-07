package ru.yandex.practicum.filmorate.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.exception.InternalServerException;

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

	protected boolean delete(String query, Long id) {
		int rowsDelete = jdbc.update(query, id);
		return rowsDelete > 0;
	}

	protected void update(String query, Object... params) {
		int rowsUpdate = jdbc.update(query, params);
		if (rowsUpdate == 0)
			throw new InternalServerException("Failed! Data is not updated!");
	}

	protected Long insert(String query, Object... params) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int idx = 1;
			for (Object param : params) {
				ps.setObject(idx++, param);
			}
			return ps;
		}, keyHolder);

		Long id = keyHolder.getKeyAs(Long.class);
		if (id == null)
			throw new InternalServerException("Failed! Data is not saved! Error: generated id == null");
		return id;
	}
}
