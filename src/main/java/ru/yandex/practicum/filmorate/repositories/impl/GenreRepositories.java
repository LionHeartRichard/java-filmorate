package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public GenreRepositories(JdbcTemplate jdbc, GenreRowMapper rowMapper) {
		super(jdbc, rowMapper, "genre", "genre_id");
	}

	@Override
	public Optional<Long> add(Genre genre) {
		log.trace("ADD: {}", Optional.ofNullable(genre).map(Genre::toString).orElse("null"));
		String queryInsert = "INSERT INTO genre (name) VALUES (?)";
		log.trace("SQL: {}", queryInsert);
		Object[] fields = {genre.getName()};
		log.trace("PreparedStatementSetter: {}", fields.toString());
		return super.addByGeneratedKey(queryInsert, fields);
	}

	@Override
	public Optional<Integer> update(Genre genre) {
		log.trace("update genre: {}", Optional.ofNullable(genre).map(Genre::toString).orElse("null"));
		String updateGenre = "UPDATE genre SET name = ?  WHERE genre_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, genre.getName());
				ps.setLong(2, genre.getGenreId());
			}
		};
		return super.update(updateGenre, pss);
	}
}
