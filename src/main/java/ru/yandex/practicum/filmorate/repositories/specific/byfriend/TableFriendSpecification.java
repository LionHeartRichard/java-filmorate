package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TableFriendSpecification implements Specification<Integer, List<Friend>> {

	private final JdbcTemplate jdbc;
	private final FriendRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static final String QUERY = "SELECT * FROM friend LIMIT ? OFFSET ?";

	@Override
	public List<Friend> specified(Integer offset, List<Friend> ans) {
		log.trace("offset: {}", offset);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, LIMIT);
				ps.setInt(2, offset);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).toList();
		return ans;
	}

}
