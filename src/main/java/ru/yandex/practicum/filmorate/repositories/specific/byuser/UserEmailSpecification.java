package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.UserRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserEmailSpecification implements Specification<String, Optional<User>> {

	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person WHERE email = ?";

	@Override
	public Optional<User> specified(String email, Optional<User> ans) {
		log.trace("SQL: {}; EMAIL: ", QUERY, email);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, email);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).findFirst();
		log.trace("Ans is present: {}", ans.isPresent());
		return ans;
	}
}
