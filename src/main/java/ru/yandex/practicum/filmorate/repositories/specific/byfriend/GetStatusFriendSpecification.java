package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

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
public class GetStatusFriendSpecification implements Specification<Long[], Optional<Friend>> {
	private final JdbcTemplate jdbc;
	private final FriendRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM friend WHERE person_id = ? AND friend_id = ?";

	@Override
	public Optional<Friend> specified(Long[] params, Optional<Friend> ans) {
		log.trace("person_id: {}", params[0]);
		log.trace("friend_id: {}", params[1]);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, params[0]);
				ps.setLong(2, params[1]);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).findFirst();
		return ans;
	}
}
