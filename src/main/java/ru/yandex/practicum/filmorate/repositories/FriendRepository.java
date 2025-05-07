package ru.yandex.practicum.filmorate.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Friend;

@Repository
public class FriendRepository extends BaseRepository<Friend> {

	private static final String FIND_ALL_QUERY = "SELECT * FROM friend";
	private static final String FIND_FRIENDS_BY_ID = "SELECT * FROM friend WHERE id = ?";
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM friend WHERE friend_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO friend(id, other_id) VALUES (?, ?) returning friend_id";

	public FriendRepository(JdbcTemplate jdbc, RowMapper<Friend> mapper) {
		super(jdbc, mapper);
	}

}
