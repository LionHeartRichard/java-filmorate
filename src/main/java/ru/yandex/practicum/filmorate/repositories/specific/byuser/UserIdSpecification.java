package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserIdSpecification implements Specification<Long, Optional<User>> {
	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person WHERE person_id=?";

	@Override
	public Optional<User> specified(Long id, Optional<User> ans) {
		log.trace("SQL: {}; ID: ", QUERY, id);
		ans = Optional.ofNullable(jdbc.queryForObject(QUERY, rowMapper, id));
		log.trace("Ans is present: {}", ans.isPresent());
		return ans;
	}
}
