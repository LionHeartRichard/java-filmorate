package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class FilmGenreRepository extends BaseRepository<FilmGenre> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM film_genre";
	private static final String FIND_BY_PK = "SELECT * FROM film_genre WHERE film_genre_id = ?";
	private static final String FIND_BY_FILM_ID = "SELECT * FROM film_genre WHERE film_id = ?";
	private static final String FIND_BY_GENRE_ID = "SELECT * FROM film_genre WHERE genre_id = ?";
	private static final String FIND_BY_IDS_QUERY = "SELECT * FROM film_genre WHERE film_id IN (%s)";

	private static final String INSERT_QUERY = "INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?)";

	private static final String DELETE_BY_PK = "DELETE FROM film_genre WHERE film_genre_id = ?";
	private static final String DELETE_BY_FILM_ID = "DELETE FROM film_genre WHERE film_id = ?";
	private static final String DELETE_BY_GENRE_ID = "DELETE FROM film_genre WHERE genre_id = ?";
	private static final String DELETE_BY_FILM_ID_AND_GENRE_ID = "DELETE FROM film_genre WHERE film_id = ? AND genre_id = ?";

	public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper) {
		super(jdbc, mapper);
	}

	public List<FilmGenre> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<FilmGenre> findByPrimaryKey(Long primaryKey) {
		return findOne(FIND_BY_PK, primaryKey);
	}

	public List<FilmGenre> findByFilmId(Long filmId) {
		return findMany(FIND_BY_FILM_ID, filmId);
	}

	public List<FilmGenre> findByGenreId(Long genreId) {
		return findMany(FIND_BY_GENRE_ID, genreId);
	}

	public FilmGenre save(FilmGenre filmGenre) {
		Long id = insert(INSERT_QUERY, filmGenre.getFilmId(), filmGenre.getGenreId());
		filmGenre.setPrimaryKey(id);
		return filmGenre;
	}

	public boolean deleteByPrimaryKey(Long primaryKey) {
		return delete(DELETE_BY_PK, primaryKey);
	}

	public boolean deleteByFilmId(Long filmId) {
		return delete(DELETE_BY_FILM_ID, filmId);
	}

	public boolean deleteByGenreId(Long genreId) {
		return delete(DELETE_BY_GENRE_ID, genreId);
	}

	public boolean deleteByFilmIdAndGenreId(Long filmId, Long genreId) {
		return delete(DELETE_BY_FILM_ID_AND_GENRE_ID, filmId, genreId);
	}

	public List<FilmGenre> findFilmGenresByIds(Set<Long> filmIds) {
		String params = String.join(",", Collections.nCopies(filmIds.size(), "?"));
		String sql = String.format(FIND_BY_IDS_QUERY, params);
		return findMany(sql, filmIds.toArray());
	}
}
