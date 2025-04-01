package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.FilmGenre;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmGenreRowMapper;

@Slf4j
@Repository
public class FilmGenreRepositories extends BaseRepo<FilmGenre> implements Repositories<FilmGenre> {

	@Autowired
	public FilmGenreRepositories(JdbcTemplate jdbc, FilmGenreRowMapper rowMapper) {
		super(jdbc, rowMapper, "film_genre", "film_id");
	}

	public Optional<Integer> remove(Long id, String genre) {
		log.trace("remove film_id: {}, genre: {}", id, genre);
		String queryDelete = "DELETE FROM film_genre WHERE film_id = ? AND name = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);
				ps.setString(2, genre);
			}
		};
		return super.update(queryDelete, pss);
	}

	@Override
	public Optional<Long> add(FilmGenre filmGenre) {
		log.trace("ADD: {}", Optional.ofNullable(filmGenre).map(FilmGenre::toString).orElse("null"));
		String queryInsert = "INSERT INTO film_genre VALUES (?,?)";
		log.trace("SQL: {}", queryInsert);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, filmGenre.getFilmId());
				ps.setString(2, filmGenre.getName());
			}
		};
		log.trace("PreparedStatementSetter: {}", pss.toString());
		return super.add(queryInsert, pss);
	}

	@Override
	public Optional<Integer> update(FilmGenre filmGenre) {
		log.trace("update filmGenre: {}", Optional.ofNullable(filmGenre).map(FilmGenre::toString).orElse("null"));
		String queryUpdate = "UPDATE film_genre SET name = ? WHERE film_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, filmGenre.getName());
				ps.setLong(2, filmGenre.getFilmId());
			}
		};
		return super.update(queryUpdate, pss);
	}

}
