package ru.yandex.practicum.filmorate.repositories.impl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.Friend;
import ru.yandex.practicum.filmorate.repositories.Repositories;
import ru.yandex.practicum.filmorate.repositories.specific.byuser.UserGetFriendSpecification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FriendRepositories implements Repositories<Friend> {

	private final UserGetFriendSpecification friendSpecification;

	@Override
	public Optional<Long> add(Friend friend) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Integer> remove(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Integer> update(Friend friend) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Collection<Friend> query(Integer userId) {
		Map<> friendSpecification.specified(userId, null)
	}

}
