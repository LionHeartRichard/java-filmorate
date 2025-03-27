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
	private static final String UPDATE_FRIEND = "UPDATE friend SET status_name = ? WHERE person_id = ? AND friend_id = ?";

	@Override
	public Optional<Long> add(Friend friend) {
		// return baseOperations.add(friend, TABLE_NAME, ID);
	}

	@Override
	public Optional<Integer> remove(Long id) {
		return baseOperations.update(REMOVE_FRIEND, pss);
	}

	@Override
	public Optional<Integer> update(Friend friend) {
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, friend.getStatusName());
				ps.setLong(2, friend.getUserId());
				ps.setLong(3, friend.getFriendId());
			}
		};
		return baseOperations.update(UPDATE_FRIEND, pss);
	}

	@Override
	public Collection<Friend> query(Integer offset) {
		return tableFriendSpecification.specified(offset, new ArrayList<>());
	}

	public Map<Long, String> query(Long userId, Integer offset) {
		Long[] params = new Long[] {userId, offset.longValue()};
		return getAllFriendSpecification.specified(params, new HashMap<>());
	}

}
