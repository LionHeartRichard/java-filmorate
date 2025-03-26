package ru.yandex.practicum.filmorate.repositories.specific.byuser;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.repositories.Specification;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;
import ru.yandex.practicum.filmorate.model.impl.Friend;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserGetFriendSpecification implements Specification<Long, Map<Long, String>> {

	private final JdbcTemplate jdbc;
	private final FriendRowMapper rowMapper;

	private static final String QUERY = "SELECT * FROM friend WHERE person_id = ?";

	@Override
	public Map<Long, String> specified(Long userId, Map<Long, String> ans) {
		ans = jdbc.query(QUERY, rowMapper, userId).stream()
				.collect(Collectors.toMap(Friend::getFriendId, Friend::getStatusName));
		return ans;
	}

}
