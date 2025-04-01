package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@Slf4j
@Repository
public class FilmRepositories extends BaseRepo<Film> implements Repositories<Film> {

	@Autowired
	public FilmRepositories(JdbcTemplate jdbc, FilmRowMapper rowMapper) {
		super(jdbc, rowMapper, "film", "film_id");
	}

	@Override
	public Optional<Long> add(Film film) {
		log.trace("add film: {}", Optional.ofNullable(film).map(Film::toString).orElse("null"));
		String queryInsert = "INSERT INTO film (name,description,release_date,duration) VALUES (?,?,?,?)";
		Object[] fields = {film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration()};
		return super.addByGeneratedKey(queryInsert, fields);
	}

	@Override
	public Optional<Integer> update(Film film) {
		log.trace("update film: {}", Optional.ofNullable(film).map(Film::toString).orElse("null"));
		String updateFilm = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ? WHERE film_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, film.getName());
				ps.setString(2, film.getDescription());
				ps.setDate(3, Date.valueOf(film.getReleaseDate()));
				ps.setInt(4, film.getDuration());
				ps.setLong(5, film.getId());
			}
		};
		return super.update(updateFilm, pss);
	}

}
