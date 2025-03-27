package ru.yandex.practicum.filmorate.repositories.specific.byfriend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;
import ru.yandex.practicum.filmorate.model.impl.Friend;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GetAllFrienSpecification implements Specification<Long[], Map<Long, String>> {

	private final JdbcTemplate jdbc;
	private final FriendRowMapper rowMapper;

	private static final Integer LIMIT = 200;
	private static final String QUERY = "SELECT * FROM friend WHERE person_id = ? LIMIT ? OFFSET ?";

	@Override
	public Map<Long, String> specified(Long[] params, Map<Long, String> ans) {
		Long userId = params[0];
		Integer offset = params[1].intValue();
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, userId);
				ps.setInt(2, LIMIT);
				ps.setInt(3, offset);
			}
		};
		ans = jdbc.query(QUERY, pss, rowMapper).stream()
				.collect(Collectors.toMap(Friend::getFriendId, Friend::getStatusName));
		return ans;
	}

}
