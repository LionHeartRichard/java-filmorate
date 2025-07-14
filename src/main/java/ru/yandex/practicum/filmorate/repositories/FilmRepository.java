package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Repository
public class FilmRepository extends BaseRepository<Film> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM film";
	private static final String FIND_BY_NAME = "SELECT * FROM film WHERE name LIKE ?";
	private static final String FIND_BY_FULL_NAME = "SELECT * FROM film WHERE name = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM film WHERE film_id = ?";
	private static final String FIND_BY_IDS_QUERY = "SELECT * FROM film WHERE film_id IN (%s)";

	private static final String INSERT_QUERY = "INSERT INTO film(name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE film_id = ?";

	private static final String DELETE_FILM_BY_ID = "DELETE FROM film WHERE film_id = ?";

	private static final String INSERT_FILM_DIRECTOR_QUERY = "INSERT INTO film_director(film_id, director_id) VALUES ";

	private static final String DELETE_FILM_DIRECTOR_BY_FILM_ID_QUERY = "DELETE FROM film_director WHERE film_id = ?";

	private static final String SELECT_NOT_IN = "SELECT * FROM film WHERE film_id NOT IN (";

	private static final String COUNT_ROWS = "SELECT COUNT(*) AS count_rows FROM film";

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
		delete(DELETE_FILM_DIRECTOR_BY_FILM_ID_QUERY, id);
		return delete(DELETE_FILM_BY_ID, id);
	}

	public void saveFilmDirectors(Film film, List<Director> directors) {

		deleteFilmDirectorsByFilmId(film.getId());

		if (directors == null || directors.isEmpty()) {
			return;
		}

		List<String> valueParams = new ArrayList<>();
		for (Director director : directors) {
			valueParams.add(String.format("(%d, %d)", film.getId(), director.getId()));
		}
		String fullQuery = INSERT_FILM_DIRECTOR_QUERY + String.join(", ", valueParams);

		batchInsert(fullQuery);
	}

	public void deleteFilmDirectorsByFilmId(Long id) {
		delete(DELETE_FILM_DIRECTOR_BY_FILM_ID_QUERY, id);
	}

	public List<Film> findFilmsByIds(Set<Long> filmIds) {
		String params = String.join(",", Collections.nCopies(filmIds.size(), "?"));
		String sql = String.format(FIND_BY_IDS_QUERY, params);
		return findMany(sql, filmIds.toArray());
	}

	public List<Film> getAllNotIdTopFilms(Set<Long> idexes) {
		StringBuilder buiderQuery = new StringBuilder(SELECT_NOT_IN);
		int[] id = {idexes.size()};
		idexes.forEach(v -> {
			--id[0];
			if (id[0] != 0) {
				buiderQuery.append(v + ",");
			} else {
				buiderQuery.append(v + ")");
			}
		});
		return findMany(buiderQuery.toString());
	}

	public Integer getCountRows() {
		int[] ans = {0};
		return jdbc.query(COUNT_ROWS, rs -> {
			while (rs.next()) {
				ans[0] = rs.getInt("count_rows");
			}
			return ans[0];
		});
	}
}
