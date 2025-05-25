package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Mpa;

@Repository
public class MpaRepository extends BaseRepository<Mpa> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM mpa";
	private static final String FIND_BY_NAME = "SELECT * FROM mpa WHERE name LIKE ?";
	private static final String FIND_BY_FULLNAME = "SELECT * FROM mpa WHERE name = ?";
	private static final String FIND_BY_ID = "SELECT * FROM mpa WHERE mpa_id = ?";
	private static final String FIND_BY_FILM_ID = "SELECT * FROM mpa WHERE film_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO mpa(film_id, name) VALUES (?, ?) RETURNING mpa_id";
	private static final String UPDATE_QUERY = "UPDATE mpa SET name = ? WHERE mpa_id = ?";

	private static final String DELETE_MPA_BY_ID = "DELETE FROM mpa WHERE mpa_id = ?";
	private static final String DELETE_MPA_BY_FILM_ID = "DELETE FROM mpa WHERE film_id = ?";

	public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
		super(jdbc, mapper);
	}

	public List<Mpa> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Mpa> findById(Long id) {
		return findOne(FIND_BY_ID, id);
	}

	public List<Mpa> findByName(String name) {
		return findMany(FIND_BY_NAME, name);
	}

	public List<Mpa> findByFullName(String fullName) {
		return findMany(FIND_BY_FULLNAME, fullName);
	}

	public Optional<Mpa> findByFilmId(Long filmId) {
		return findOne(FIND_BY_FILM_ID, filmId);
	}

	public Mpa save(Mpa mpa) {
		Long id = insert(INSERT_QUERY, mpa.getFilmId(), mpa.getName());
		mpa.setId(id);
		return mpa;
	}

	public Mpa update(Mpa mpa) {
		update(UPDATE_QUERY, mpa.getName(), mpa.getId());
		return mpa;
	}

	public boolean deleteById(Long mpaId) {
		return delete(DELETE_MPA_BY_ID, mpaId);
	}

	public boolean deleteByFilmId(Long filmId) {
		return delete(DELETE_MPA_BY_FILM_ID, filmId);
	}

}
