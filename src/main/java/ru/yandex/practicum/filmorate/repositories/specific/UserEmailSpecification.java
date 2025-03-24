package ru.yandex.practicum.filmorate.repositories.specific;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEmailSpecification implements Specification<String, Optional<User>> {

	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person WHERE email=?";

	@Override
	public Optional<User> specified(String email, Optional<User> ans) {
		log.trace("SQL: {}; EMAIL: ", QUERY, email);
		ans = Optional.ofNullable(jdbc.queryForObject(QUERY, rowMapper, email));
		log.trace("Ans is present: {}", ans.isPresent());
		return ans;
	}

}
