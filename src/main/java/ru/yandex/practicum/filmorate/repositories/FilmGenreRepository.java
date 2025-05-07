package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.FilmGenre;

@Repository
public class FilmGenreRepository extends BaseRepository<FilmGenre> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM film_genre";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM film_genre WHERE film_id = ? AND genre_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?)";

	// TODO add method in package REPOSITORIES - delete

	public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper) {
		super(jdbc, mapper);
	}

	public List<FilmGenre> findByAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<FilmGenre> findById(Long filmId, Long genreId) {
		return findOne(FIND_BY_ID_QUERY, filmId, genreId);
	}

	public FilmGenre save(Long filmId, Long genreId) {
		FilmGenre filmGenre = FilmGenre.builder().filmId(filmId).genreId(genreId).build();
		update(INSERT_QUERY, filmId, genreId);
		return filmGenre;
	}

}
