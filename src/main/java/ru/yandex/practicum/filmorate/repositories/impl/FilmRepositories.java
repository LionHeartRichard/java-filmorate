package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.BaseOperations;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.specific.byfilm.TableFilmSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.byfilm.FilmIdSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.byfilm.FilmNameSpecification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmRepositories implements Repositories<Film> {

	private final TableFilmSpecification tableFilmSpecification;
	private final FilmNameSpecification filmFindByName;
	private final FilmIdSpecification filmFindByid;
	private final BaseOperations<Film> operations;

	private static final String UPDATE_FILM = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, rating_name = ?  WHERE film_id = ?";
	private static final String TBALE_NAME = "film";
	private static final String ID = "film_id";

	@Override
	public Optional<Long> add(Film film) {
		log.trace("add film: {}", film.toString());
		return operations.add(film, TBALE_NAME, ID);
	}

	@Override
	public Optional<Integer> remove(Long id) {
		log.trace("remove film, id: {}", id);
		return operations.remove(id, TBALE_NAME, ID);
	}

	@Override
	public Optional<Integer> update(Film film) {
		log.trace("update film: {}", film.toString());
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, film.getName());
				ps.setString(2, film.getDescription());
				ps.setDate(3, Date.valueOf(film.getReleaseDate()));
				ps.setInt(4, film.getDuration());
				ps.setString(5, film.getRatingName());
				ps.setLong(6, film.getId());
			}
		};
		return operations.update(UPDATE_FILM, pss);
	}

	@Override
	public Collection<Film> query(Integer offset) {
		log.trace("film find all, OFFSET: {}", offset);
		return tableFilmSpecification.specified(offset, new ArrayList<>());
	}

	public Collection<Film> query(String name) {
		log.trace("film find by name: {}", name);
		return filmFindByName.specified(name, new ArrayList<>());
	}

	public Optional<Film> query(Long id) {
		log.trace("film find by id: {}", id);
		return filmFindByid.specified(id, Optional.empty());
	}

}
