package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	@Override
	public Optional<Long> add(Friend friend) {
		String queryInsert = "INSERT INTO friend (person_id, friend_id) VALUES (?,?)";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, friend.getUserId());
				ps.setLong(2, friend.getFriendId());
			}
		};
		return super.add(queryInsert, pss);
	}

	public Optional<Long> add(Long userId, Long friendId) {
		String queryInsert = "INSERT INTO friend (person_id, friend_id) VALUES (?,?)";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, userId);
				ps.setLong(2, friendId);
			}
		};
		return super.add(queryInsert, pss);
	}

	public Set<Long> getFriends(Long id) {
		String querySelect = String.format("SELECT friend_id FROM friend WHERE person_id = %d", id);
		return jdbc.query(querySelect, (rs) -> {
			Set<Long> ans = new HashSet<>();
			while (rs.next()) {
				ans.add(rs.getLong("friend_id"));
			}
			return ans;
		});
	}

	@Deprecated
	@Override
	public Optional<Integer> update(Friend t) {
		return Optional.empty();
	}

	public Optional<Integer> removeRow(Long userId, Long friendId) {
		String queryDelete = "DELETE FROM friend WHERE person_id = ? AND friend_id = ?";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, userId);
				ps.setLong(2, friendId);
			}
		};
		return super.update(queryDelete, pss);
	}

}
