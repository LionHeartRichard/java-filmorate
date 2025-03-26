package ru.yandex.practicum.filmorate.repositories.specific;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Status;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.StatusRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StatusSpecification implements Specification<Integer, Set<String>> {

	private final JdbcTemplate jdbc;
	private final StatusRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static String QUERY = "SELECT * FROM status LIMIT ? OFFSET ?";

	@Override
	public Set<String> specified(Integer offset, Set<String> ans) {
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, LIMIT);
				ps.setInt(2, offset);
			}
		};
		ans = jdbc.query(QUERY, pss, rowMapper).stream().map(Status::getName).collect(Collectors.toSet());
		return ans;
	}

}
