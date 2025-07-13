package ru.yandex.practicum.filmorate.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class FilmRepository extends BaseRepository<Film> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM film";
	private static final String FIND_BY_NAME = "SELECT * FROM film WHERE name LIKE ?";
	private static final String FIND_BY_FULL_NAME = "SELECT * FROM film WHERE name = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM film WHERE film_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO film(name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?";

	private static final String DELETE_FILM_BY_ID = "DELETE FROM film WHERE film_id = ?";

	public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
		super(jdbc, mapper);
	}

	public List<Film> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Film> findById(Long id) {
		return findOne(FIND_BY_ID_QUERY, id);
	}

	public List<Film> findByName(String name) {
		return findMany(FIND_BY_NAME, name);
	}

	public List<Film> findByFullName(String name) {
		return findMany(FIND_BY_FULL_NAME, name);
	}

	public Film save(Film film) {
		Long id = insert(INSERT_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
				film.getMpaId());
		film.setId(id);
		return film;
	}

	public Film update(Film film) {
		update(UPDATE_QUERY, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
				film.getMpaId(), film.getId());
		return film;
	}

	public boolean deleteFilmById(Long id) {
		return delete(DELETE_FILM_BY_ID, id);
	}

	public List<Long> findCommonFilms(Long userId, Long friendId) {
		String query = "SELECT fp.FILM_ID FROM FILM_PERSON fp WHERE fp.FILM_ID IN (" +
				"SELECT FILM_ID FROM FILM_PERSON WHERE PERSON_ID IN (%d, %d) GROUP BY FILM_ID HAVING COUNT(*) = 2)" +
				" GROUP BY fp.FILM_ID ORDER BY COUNT(*) DESC";
		return jdbc.query(String.format(query, userId, friendId),
				(rs) -> {
			List<Long> list = new LinkedList<>();
			while (rs.next())
				list.add(rs.getLong("film_id"));
			return list;
		});
	 }
}
