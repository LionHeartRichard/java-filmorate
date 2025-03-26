package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
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
public class UserFindAllSpecification implements Specification<Integer, List<User>> {

	private final JdbcTemplate jdbc;
	private final UserRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM person LIMIT ? OFFSET ?";
	private static final Integer PAGE_LIMIT = 100;

	@Override
	public List<User> specified(Integer offset, List<User> ans) {
		log.trace("SQL: {}", QUERY);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, PAGE_LIMIT);
				ps.setInt(2, offset);
			}

		};
		ans = jdbc.query(QUERY, pss, rowMapper);
		return ans;
	}
}
