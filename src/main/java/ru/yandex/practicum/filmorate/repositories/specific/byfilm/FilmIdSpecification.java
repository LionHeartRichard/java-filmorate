package ru.yandex.practicum.filmorate.repositories.specific.byfilm;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmIdSpecification implements Specification<Long, Optional<Film>> {
	private final JdbcTemplate jdbc;
	private final FilmRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM film WHERE film_id=?";

	@Override
	public Optional<Film> specified(Long id, Optional<Film> ans) {
		log.trace("Find film by id: {}", id);
		ans = Optional.ofNullable(jdbc.queryForObject(QUERY, rowMapper, id));
		log.trace("SQL :{}", QUERY);
		return ans;
	}

}
