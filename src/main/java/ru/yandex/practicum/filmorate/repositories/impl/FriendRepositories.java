package ru.yandex.practicum.filmorate.repositories.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.repositories.BaseOperations;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.specific.byfriend.TableFriendSpecification;
import ru.yandex.practicum.filmorate.repositories.specific.byfriend.GetAllFrienSpecification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendRepositories implements Repositories<Friend> {

	private final BaseOperations<Friend> baseOperations;
	private final TableFriendSpecification tableFriendSpecification;
	private final GetAllFrienSpecification getAllFriendSpecification;

	private static final String ID = "person_id";
	private static final String TABLE_NAME = "friend";

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
		return baseOperations.add(insertFriend, pss);
	}

	@Override
	public Optional<Integer> remove(Long userId) {
		log.trace("remove id: {}", userId);
		return baseOperations.remove(userId, TABLE_NAME, ID);
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
		return baseOperations.update(removeFriend, pss);
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
		return baseOperations.update(updateFriend, pss);
	}

	@Override
	public Collection<Friend> query(Integer offset) {
		return tableFriendSpecification.specified(offset, new ArrayList<>());
	}

	public Map<Long, String> query(Long userId, Integer offset) {
		log.trace("userId: {}, offset: {}", userId, offset);
		Long[] params = new Long[] {userId, offset.longValue()};
		return getAllFriendSpecification.specified(params, new HashMap<>());
	}

}
