package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

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
public class GetFreindByStatusSpecification implements Specification<Object[], Set<Long>> {

	private final JdbcTemplate jdbc;
	private final FriendRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static final String QUERY = "SELECT * FROM friend WHERE person_id = ? AND status_name = ? LIMIT ? OFFSET ?";

	@Override
	public Set<Long> specified(Object[] params, Set<Long> ans) {
		Long userId = (Long) params[0];
		String statusFriend = String.valueOf(params[1]);
		Integer offset = (Integer) params[2];
		log.trace("userId: {}, statusFriend: {}, offset: {}", userId, statusFriend, offset);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, userId);
				ps.setString(2, statusFriend);
				ps.setInt(3, LIMIT);
				ps.setInt(4, offset);
			}
		};
		ans = jdbc.queryForStream(QUERY, pss, rowMapper).map(Friend::getFriendId).collect(Collectors.toSet());
		return ans;
	}

}
