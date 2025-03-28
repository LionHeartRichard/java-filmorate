package ru.yandex.practicum.filmorate.repositories.specific.bylikefilm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.repositories.Specification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GetCountLikeByFilmSpecification implements Specification<Long, Integer> {

	private final JdbcTemplate jdbc;
	private static final String QUERY = "SELECT COUNT(*) FROM film_person WHERE film_id = ?";

	@Override
	public Integer specified(Long filmId, Integer ans) {
		log.trace("filmId: {}", filmId);
		ans = jdbc.queryForObject(QUERY, Integer.class, filmId);
		return ans;
	}

}
