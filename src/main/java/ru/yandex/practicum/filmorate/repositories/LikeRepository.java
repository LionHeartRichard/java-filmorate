package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Like;

@Repository
public class LikeRepository extends BaseRepository<Like> {

//	private static final String TOP_FILMS = "SELECT film_id FROM film_person GROUP BY film_id ORDER BY person_id DESC LIMIT ?";

	private static final String FIND_ALL_QUERY = "SELECT * FROM film_person";
	private static final String FIND_BY_FILM_ID = "SELECT * FROM film_person WHERE film_id = ?";
	private static final String FIND_BY_USER_ID = "SELECT * FROM film_person WHERE person_id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM film_person WHERE film_person_id = ?";
	private static final String FIND_LIKE = "SELECT * FROM film_person WHERE film_id = ? AND person_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO film_person(film_id, person_id) VALUES (?, ?)";

	private static final String DELETE_LIKE = "DELETE FROM film_person WHERE film_id = ? AND person_id = ?";
	private static final String DELETE_BY_ID = "DELETE FROM film_person WHERE film_person_id = ?";
	private static final String DELETE_BY_USER_ID = "DELETE FROM film_person WHERE person_id = ?";

	private static final String DELETE_BY_FILM_ID = "DELETE FROM film_person WHERE film_id = ?";

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

	public boolean deleteLike(Long filmId, Long userId) {
		return delete(DELETE_LIKE, filmId, userId);
	}

//	public Map<Long, Integer> getTopFilms(int limit, Long genreId, Integer year) {
//
//		final String beginQuery = "SELECT fp.film_id, COUNT(*) AS like_count "
//				+ "FROM FILM_PERSON fp, FILM f, FILM_GENRE fg " + "WHERE f.film_id = fp.film_id "
//				+ "AND fg.film_id = f.film_id ";
//
//		final String endQuery = String.format(" GROUP BY fp.film_id " + "ORDER BY like_count DESC " + "LIMIT %d",
//				limit);
//
//		final String query;
//
//		if (genreId != null && year != null) {
//			query = beginQuery + String.format(" AND fg.genre_id = %d AND YEAR(f.release_date) = %d", genreId, year)
//					+ endQuery;
//		} else if (genreId != null) {
//			query = beginQuery + String.format(" AND fg.genre_id = %d", genreId) + endQuery;
//		} else if (year != null) {
//			query = beginQuery + String.format(" AND YEAR(f.release_date) = %d", year) + endQuery;
//		} else {
//			query = beginQuery + endQuery;
//		}
//
//		Map<Long, Integer> ans = new LinkedHashMap<>();
//		return jdbc.query(query, (rs) -> {
//			while (rs.next()) {
//				Long filmId = rs.getLong("film_id");
//				Integer likeCount = rs.getInt("like_count");
//				ans.put(filmId, likeCount);
//			}
//			return ans;
//		});
//	}

	public List<Long> getTopFilms(int limit, Long genreId, Integer year) {
		String format =
				"SELECT f.FILM_ID FROM FILM f" +
				" LEFT JOIN FILM_GENRE fg ON f.FILM_ID = fg.FILM_ID" +
				" LEFT JOIN (SELECT fp.FILM_ID, count(*) AS likes FROM FILM_PERSON fp GROUP BY fp.FILM_ID) AS lf" +
				" ON f.FILM_ID = lf.FILM_ID" +
				" %s" +
				" ORDER BY f.FILM_ID DESC" +
				" LIMIT %d;";
		String query;
		if (genreId != null && year != null)
			query = String.format(format,
					String.format("WHERE YEAR(f.RELEASE_DATE) = %d AND fg.GENRE_ID = %d", year, genreId), limit);
		else if (genreId != null)
			query = String.format(format, String.format("WHERE fg.GENRE_ID = %d", genreId), limit);
		else if (year != null)
			query = String.format(format, String.format("WHERE YEAR(f.RELEASE_DATE) = %d", year), limit);
		else
			query = String.format(format, "", limit);
		return jdbc.query(query, (rs, rowNum) -> rs.getLong("film_id"));
	}
}
