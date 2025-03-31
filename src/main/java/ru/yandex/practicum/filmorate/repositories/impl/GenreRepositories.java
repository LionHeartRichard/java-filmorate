package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Genre;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.GenreRowMapper;

@Slf4j
@Repository
public class GenreRepositories extends BaseRepo<Genre> implements Repositories<Genre> {

	public GenreRepositories(JdbcTemplate jdbc, GenreRowMapper rowMapper) {
		super(jdbc, rowMapper, "genre", "name");
	}

	@Override
	public Optional<Long> add(Genre genre) {
		log.trace("ADD: {}", Optional.ofNullable(genre).map(Genre::toString).orElse("null"));
		String queryInsert = "INSERT INTO genre VALUES (?)";
		log.trace("SQL: {}", queryInsert);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, genre.getName());
			}
		};
		log.trace("PreparedStatementSetter: {}", pss.toString());
		return super.add(queryInsert, pss);
	}

	@Deprecated
	@Override
	public Optional<Integer> update(Genre t) {
		return Optional.empty();
	}

	public Optional<Integer> remove(String genreName) {
		String queryDelete = "DELETE FROM genre WHERE name = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, genreName);
			}
		};
		return super.update(queryDelete, pss);
	}

	@Deprecated
	@Override
	public Optional<Integer> remove(Long id) {
		return null;
	}

}
