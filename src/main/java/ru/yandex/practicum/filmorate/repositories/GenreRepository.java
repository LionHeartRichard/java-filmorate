package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Genre;

@Repository
public class GenreRepository extends BaseRepository<Genre> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM genre";
	private static final String FIND_BY_NAME = "SELECT * FROM genre WHERE name LIKE ?";
	private static final String FIND_BY_FULL_NAME = "SELECT * FROM genre WHERE name = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM genre WHERE genre_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO genre(name) VALUES (?) RETURNING genre_id";
	private static final String UPDATE_QUERY = "UPDATE genre SET name = ? WHERE genre_id = ?";

	private static final String DELETE_GENRE_BY_ID = "DELETE FROM genre WHERE genre_id = ?";

	public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
		super(jdbc, mapper);
	}

	public List<Genre> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Genre> findById(Long id) {
		return findOne(FIND_BY_ID_QUERY, id);
	}

	public List<Genre> findByName(String name) {
		return findMany(FIND_BY_NAME, name);
	}

	public List<Genre> findByFullName(String fullName) {
		return findMany(FIND_BY_FULL_NAME, fullName);
	}

	public Genre save(Genre genre) {
		Long id = insert(INSERT_QUERY, genre.getName());
		genre.setGenreId(id);
		return genre;
	}

	public Genre update(Genre genre) {
		update(UPDATE_QUERY, genre.getName(), genre.getGenreId());
		return genre;
	}

	public boolean deleteGenreById(Long id) {
		return delete(DELETE_GENRE_BY_ID, id);
	}
}
