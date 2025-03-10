package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {
	private final UserStorage userStorage;

	public Collection<User> addFriend(final Long id, final Long friendId) {
		User user = userStorage.findById(id);
		User friend = userStorage.findById(friendId);
		log.trace("Начало обработки в СЕРВИСЕ. Добавление в друзья");
		Set<Long> userIdxes = user.getFriends();
		Set<Long> friendIdxes = friend.getFriends();
		if (!userIdxes.contains(friendId)) {
			userIdxes.add(friendId);
			friendIdxes.add(id);
			user.setFriends(userIdxes);
			friend.setFriends(friendIdxes);
			userStorage.update(user);
			userStorage.update(friend);
			log.trace("Пользователи: {}, {} теперь друзья", user.toString(), friend.toString());
		}
		return new ArrayList<>(Arrays.asList(user, friend));
	}

	public Collection<User> deleteFriend(final Long id, final Long friendId) {
		User user = userStorage.findById(id);
		User friend = userStorage.findById(friendId);
		log.trace("Начало обработки в СЕРВИСЕ. Удаление из друзей");
		Set<Long> userIdxes = user.getFriends();
		Set<Long> friendIdxes = friend.getFriends();
		if (!userIdxes.contains(friendId)) {
			userIdxes.remove(friendId);
			friendIdxes.remove(id);
			user.setFriends(userIdxes);
			friend.setFriends(friendIdxes);
			userStorage.update(user);
			userStorage.update(friend);
			log.trace("Пользователи: {}, {} больше не дружат", user.toString(), friend.toString());
		}
		return new ArrayList<>(Arrays.asList(user, friend));
	}

	public Collection<User> getFriends(final Long id) {
		User user = userStorage.findById(id);
		Set<Long> setIdFriends = user.getFriends();
		log.trace("Начало обработки в СЕРВИСЕ. Получение списка друзей");
		return setIdFriends.stream().map(userStorage::findById).toList();
	}

	public Collection<User> getCommonFriends(final Long id, final Long otherId) {
		User user = userStorage.findById(id);
		User other = userStorage.findById(otherId);
		log.trace("Обработка в СЕРВИСЕ. Получение списка общих друзей");
		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = other.getFriends();
		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
		return common.stream().map(userStorage::findById).toList();
	}

}
