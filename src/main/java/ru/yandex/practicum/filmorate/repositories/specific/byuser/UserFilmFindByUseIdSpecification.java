package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.FilmUser;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FilmUserRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserFilmFindByUseIdSpecification implements Specification<Long, Set<Long>> {

	private final JdbcTemplate jdbc;
	private final FilmUserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM film_person WHERE person_id = ?";

	@Override
	public Set<Long> specified(Long userId, Set<Long> ans) {
		ans = jdbc.query(QUERY, rowMapper, userId).stream().map(FilmUser::getFilmId).collect(Collectors.toSet());
		return ans;
	}

}
