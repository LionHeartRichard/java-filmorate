package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Like;

@Repository
public class LikeRepository extends BaseRepository<Like> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM film_person";
	private static final String FIND_BY_FILM_ID = "SELECT * FROM film_person WHERE film_id = ?";
	private static final String FIND_BY_USER_ID = "SELECT * FROM film_person WHERE person_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM film_person WHERE film_person_id = ?";
	private static final String FIND_LIKE = "SELECT * FROM film_person WHERE film_id = ? AND person_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO film_person(film_id, person_id) VALUES (?, ?)";

	private static final String DELETE_BY_ID = "DELETE FROM film_person WHERE film_person_id = ?";
	private static final String DELETE_BY_FILM_ID = "DELETE FROM film_person WHERE film_id = ?";
	private static final String DELETE_BY_USER_ID = "DELETE FROM film_person WHERE person_id = ?";

	public LikeRepository(JdbcTemplate jdbc, RowMapper<Like> mapper) {
		super(jdbc, mapper);
	}

	public List<Like> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Like> findByPrimaryKey(Long primaryKey) {
		return findOne(FIND_BY_PK, primaryKey);
	}

	public Optional<Like> findLike(Long filmId, Long userId) {
		return findOne(FIND_LIKE, filmId, userId);
	}

	public List<Like> findByFilmId(Long filmId) {
		return findMany(FIND_BY_FILM_ID, filmId);
	}

	public List<Like> findByUserId(Long userId) {
		return findMany(FIND_BY_USER_ID, userId);
	}

	public Like save(Like like) {
		Long id = insert(INSERT_QUERY, like.getFilmId(), like.getUserId());
		like.setLikeId(id);
		return like;
	}

	public boolean deleteById(Long likeId) {
		return delete(DELETE_BY_ID, likeId);
	}

	public boolean deleteByFilmId(Long filmId) {
		return delete(DELETE_BY_FILM_ID, filmId);
	}

	public boolean deleteByUserId(Long userId) {
		return delete(DELETE_BY_USER_ID, userId);
	}
}
