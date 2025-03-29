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
public class UserIdSpecification implements Specification<Long, Optional<User>> {
	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person WHERE person_id = ?";

	@Override
	public Optional<User> specified(Long id, Optional<User> ans) {
		log.trace("SQL: {}; ID: ", QUERY, id);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).findFirst();
		log.trace("Ans is present: {}", ans.isPresent());
		return ans;
	}
}
