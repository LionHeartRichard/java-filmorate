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
import ru.yandex.practicum.filmorate.model.impl.Mpa;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.MpaRowMapper;

@Slf4j
@Repository
public class MpaRepositories extends BaseRepo<Mpa> implements Repositories<Mpa> {

	@Autowired
	public MpaRepositories(JdbcTemplate jdbc, MpaRowMapper rowMapper) {
		super(jdbc, rowMapper, "mpa", "mpa_id");
	}

	@Override
	public Optional<Long> add(Mpa mpa) {
		log.trace("insert mpa: {}", Optional.ofNullable(mpa).map(Mpa::toString).orElse("null"));
		String queryInsert = "INSERT INTO mpa (name) VALUES (?)";
		Object[] fields = {mpa.getName()};
		return super.addByGeneratedKey(queryInsert, fields);
	}

	@Override
	public Optional<Integer> update(Mpa mpa) {
		log.trace("update mpa: {}", Optional.ofNullable(mpa).map(Mpa::toString).orElse("null"));
		String updateGenre = "UPDATE mpa SET name = ?  WHERE mpa_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, mpa.getName());
				ps.setLong(2, mpa.getMpaId());
			}
		};
		return super.update(updateGenre, pss);
	}

	public Optional<Integer> removeFilm(Long filmId) {
		log.trace("remove film in mpa filmId: {}", filmId);
		String queryDelete = "DELETE FROM mpa WHERE film_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, filmId);
			}
		};
		return super.update(queryDelete, pss);
	}

}
