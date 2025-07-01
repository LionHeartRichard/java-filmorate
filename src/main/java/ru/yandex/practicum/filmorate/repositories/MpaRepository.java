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
	private static final String FIND_BY_ID = "SELECT * FROM mpa WHERE id = ?";

	private static final String INSERT_QUERY = "INSERT INTO mpa(name) VALUES (?)";

	private static final String DELETE_MPA_BY_ID = "DELETE FROM mpa WHERE id = ?";

	public MpaRepository(JdbcTemplate jdbc, RowMapper<Mpa> mapper) {
		super(jdbc, mapper);
	}

	public List<Mpa> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Mpa> findById(Long id) {
		return findOne(FIND_BY_ID, id);
	}

	public Mpa save(Mpa mpa) {
		Long id = insert(INSERT_QUERY, mpa.getName());
		mpa.setId(id);
		return mpa;
	}

	public boolean deleteById(Long mpaId) {
		return delete(DELETE_MPA_BY_ID, mpaId);
	}

}
