package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.repositories.BaseRepo;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.rowmapper.FriendRowMapper;

@Slf4j
@Repository
public class FriendRepositories extends BaseRepo<Friend> implements Repositories<Friend> {

	@Autowired
	public FriendRepositories(JdbcTemplate jdbc, FriendRowMapper rowMapper) {
		super(jdbc, rowMapper, "friend", "person_id");
	}

	public Optional<Integer> remove(Long userId, Long friendId) {
		log.trace("remove userId: {}, friendId: {}", userId, friendId);
		String removeFriend = "DELETE FROM friend WHERE person_id = ? AND friend_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, userId);
				ps.setLong(2, friendId);
			}
		};
		return super.update(removeFriend, pss);
	}

	@Override
	public Optional<Long> add(Friend friend) {
		log.trace("ADD: {}", Optional.ofNullable(friend).map(Friend::toString).orElse("null"));
		String insertFriend = "INSERT INTO friend VALUES (?,?,?)";
		log.trace("SQL: {}", insertFriend);
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, friend.getUserId());
				ps.setLong(2, friend.getFriendId());
				ps.setString(3, friend.getStatusName());
			}
		};
		log.trace("PreparedStatementSetter: {}", pss.toString());
		return super.add(insertFriend, pss);
	}

	@Override
	public Optional<Integer> update(Friend friend) {
		log.trace("update friend: {}", Optional.ofNullable(friend).map(Friend::toString).orElse("null"));
		String updateFriend = "UPDATE friend SET status_name = ? WHERE person_id = ? AND friend_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, friend.getStatusName());
				ps.setLong(2, friend.getUserId());
				ps.setLong(3, friend.getFriendId());
			}
		};
		return super.update(updateFriend, pss);
	}

	public Set<Long> getFriends(Long id) {
		log.trace("getFriends({})", id);
		String querySelect = String.format("SELECT friend_id FROM friend WHERE person_id = %d", id);
		return jdbc.query(querySelect, rs -> {
			Set<Long> ans = new HashSet<>();
			while (rs.next()) {
				ans.add(rs.getLong("friend_id"));
			}
			return ans;
		});
	}

}
