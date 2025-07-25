package ru.yandex.practicum.filmorate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.model.Friend;

@Repository
public class FriendRepository extends BaseRepository<Friend> {

	private static final String COMMON_FRIENDS = "SELECT f.* FROM friend AS f WHERE f.other_id IN (SELECT sub.other_id FROM friend AS sub WHERE id IN (?, ?) GROUP BY other_id HAVING COUNT(*) > 1)";

	private static final String FIND_ALL_QUERY = "SELECT * FROM friend";
	private static final String FIND_FRIENDS_BY_ID = "SELECT * FROM friend WHERE id = ?";
	private static final String FIND_BY_PK = "SELECT * FROM friend WHERE friend_id = ?";
	private static final String FIND_FRIENDS_BY_ID_AND_OTHER_ID = "SELECT * FROM friend WHERE id = ? AND other_id = ?";

	private static final String INSERT_QUERY = "INSERT INTO friend(id, other_id) VALUES (?, ?)";

	private static final String DELETE_BY_PK = "DELETE FROM friend WHERE friend_id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE FROM friend WHERE id = ? OR other_id = ?";
	private static final String DELETE_FRIEND = "DELETE FROM friend WHERE id = ? AND other_id = ?";

	public FriendRepository(JdbcTemplate jdbc, RowMapper<Friend> mapper) {
		super(jdbc, mapper);
	}

	public List<Friend> findAll() {
		return findMany(FIND_ALL_QUERY);
	}

	public Optional<Friend> findByPrimaryKey(Long primaryKey) {
		return findOne(FIND_BY_PK, primaryKey);
	}

	public Optional<Friend> findFriend(Long id, Long otherId) {
		return findOne(FIND_FRIENDS_BY_ID_AND_OTHER_ID, id, otherId);
	}

	public List<Friend> findFriendsById(Long id) {
		return findMany(FIND_FRIENDS_BY_ID, id);
	}

	public Friend save(Friend friend) {
		Long id = insert(INSERT_QUERY, friend.getUserId(), friend.getFriendId());
		friend.setPrimaryKey(id);
		return friend;
	}

	public boolean deleteByPrimaryKey(Long primaryKey) {
		return delete(DELETE_BY_PK, primaryKey);
	}

	public boolean deleteUserById(Long id) {
		return delete(DELETE_USER_BY_ID, id, id);
	}

	public boolean deleteFriend(Long id, Long friendId) {
		return delete(DELETE_FRIEND, id, friendId);
	}

	public List<Friend> commonFriends(Long id, Long otherId) {
		return findMany(COMMON_FRIENDS, id, otherId);
	}
}
