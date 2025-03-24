package ru.yandex.practicum.filmorate.repositories.specific;

import java.util.Collection;

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
public class UserFindAllSpecification implements Specification<Object, Collection<User>> {

	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person";

	@Override
	public Collection<User> specified(Object plug, Collection<User> ans) {
		log.trace("SQL: {}", QUERY);
		ans = jdbc.query(QUERY, rowMapper);
		return ans;
	}

}
