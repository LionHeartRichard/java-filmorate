package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = friend.getFriends();
		userIdexes = userIdexes == null ? new HashSet<>() : userIdexes;
		otherIdexes = otherIdexes == null ? new HashSet<>() : otherIdexes;
		return helperAddFriend(userIdexes, otherIdexes, user, friend, id, friendId);
	}

	private Collection<User> helperAddFriend(Set<Long> userIdexes, Set<Long> otherIdexes, User user, User friend,
			Long id, Long friendId) {
		if (!userIdexes.contains(friendId)) {
			userIdexes.add(friendId);
			otherIdexes.add(id);
			user.setFriends(userIdexes);
			friend.setFriends(otherIdexes);
			userStorage.update(user);
			userStorage.update(friend);
			log.trace("Пользователи: {}, {} теперь друзья", user.toString(), friend.toString());
		}
		return new ArrayList<>(Arrays.asList(user, friend));
	}

	public Collection<User> deleteFriend(final Long id, final Long otherId) {
		User user = userStorage.findById(id);
		User other = userStorage.findById(otherId);
		log.trace("Начало обработки в СЕРВИСЕ. Удаление из друзей");
		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = other.getFriends();
		userIdexes = userIdexes == null ? new HashSet<>() : userIdexes;
		otherIdexes = otherIdexes == null ? new HashSet<Long>() : otherIdexes;
		return helperDeleteFriend(userIdexes, otherIdexes, id, otherId, user, other);
	}

	private Collection<User> helperDeleteFriend(Set<Long> userIdexes, Set<Long> otherIdexes, Long id, Long otherId,
			User user, User other) {
		if (userIdexes.contains(otherId)) {
			userIdexes.remove(otherId);
			user.setFriends(userIdexes);
			userStorage.update(user);
			log.trace("Пользователь: {}, удален из друзей у: {}", other.toString(), user.toString());
		}
		if (otherIdexes.contains(id)) {
			otherIdexes.remove(id);
			other.setFriends(otherIdexes);
			userStorage.update(other);
			log.trace("Пользователь: {}, удален из друзей у: {}", user.toString(), other.toString());
		}
		return new ArrayList<>(Arrays.asList(user, other));
	}

	public Collection<User> getFriends(final Long id) {
		User user = userStorage.findById(id);
		Set<Long> friendIdexes = user.getFriends();
		friendIdexes = friendIdexes == null ? new HashSet<>() : friendIdexes;
		log.trace("Начало обработки в СЕРВИСЕ. Получение списка друзей");
		return friendIdexes.stream().map(userStorage::findById).toList();
	}

	public Collection<User> getCommonFriends(final Long id, final Long otherId) {
		User user = userStorage.findById(id);
		User other = userStorage.findById(otherId);
		log.trace("Обработка в СЕРВИСЕ. Получение списка общих друзей");

		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = other.getFriends();

		userIdexes = userIdexes == null ? new HashSet<>() : userIdexes;
		otherIdexes = otherIdexes == null ? new HashSet<>() : otherIdexes;

		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
		common = common == null ? new HashSet<>() : common;
		return common.stream().map(userStorage::findById).toList();
	}

}
