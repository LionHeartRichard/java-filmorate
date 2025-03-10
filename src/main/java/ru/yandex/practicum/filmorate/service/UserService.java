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
import ru.yandex.practicum.filmorate.util.ApiValidator;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

	private final ApiValidator validator;
	private final UserStorage userStorage;

	public Collection<User> addFriend(final Long id, final Long friendId) {
		validator.positiveValue(id, String.format("Передан отрицательный id: %d", id));
		validator.positiveValue(friendId, String.format("Передан отрицательный friendId: %d", friendId));
		User user = userStorage.findById(id);
		User friend = userStorage.findById(friendId);
		log.trace("Начало обработки в СЕРВИСЕ. Добавление в друзья");
		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = friend.getFriends();
		userIdexes = userIdexes == null ? new HashSet<>() : userIdexes;
		otherIdexes = otherIdexes == null ? new HashSet<>() : otherIdexes;
		return helperAddFriend(userIdexes, otherIdexes, user, friend, id, friendId);
	}

	private Collection<User> helperAddFriend(final Set<Long> userIdexes, final Set<Long> otherIdexes, final User user,
			final User friend, final Long id, final Long friendId) {
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

	public Collection<User> deleteFriend(final Long id, final Long friendId) {
		validator.positiveValue(id, String.format("Передан отрицательный id: %d", id));
		validator.positiveValue(friendId, String.format("Передан отрицательный friendId: %d", friendId));
		User user = userStorage.findById(id);
		User friend = userStorage.findById(friendId);
		log.trace("Начало обработки в СЕРВИСЕ. Удаление из друзей");
		Set<Long> userIdexes = user.getFriends();
		Set<Long> otherIdexes = friend.getFriends();
		return helperDeleteFriend(userIdexes, otherIdexes, id, friendId, user, friend);
	}

	private Collection<User> helperDeleteFriend(final Set<Long> userIdexes, final Set<Long> otherIdexes, final Long id,
			final Long friendId, final User user, final User friend) {
		validator.collectionNotNull(userIdexes,
				String.format("Удалить друзей у пользователя: %s невозможно. Поле друзья = null", user.toString()));
		validator.collectionNotNull(userIdexes,
				String.format("Удалить друзей у пользователя: %s невозможно. Поле друзья = null", friend.toString()));
		if (userIdexes.contains(friendId) && otherIdexes.contains(id)) {
			userIdexes.remove(friendId);
			otherIdexes.remove(id);
			user.setFriends(userIdexes);
			friend.setFriends(otherIdexes);
			userStorage.update(user);
			userStorage.update(friend);
			log.trace("Пользователи: {}, {} больше не дружат", user.toString(), friend.toString());
		}
		return new ArrayList<>(Arrays.asList(user, friend));
	}

	public Collection<User> getFriends(final Long id) {
		validator.positiveValue(id, String.format("Передан отрицательный id: %d", id));
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
		validator.collectionNotNull(userIdexes, String.format(
				"Найти общих друзей не представляет возможности. У пользователя: %s нет друзей", user.toString()));
		Set<Long> otherIdexes = other.getFriends();
		validator.collectionNotNull(otherIdexes, String.format(
				"Найти общих друзей не представляет возможности. У пользователя: %s нет друзей", user.toString()));
		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
		validator.collectionNotNull(common, "Общих друзей не найдено!");
		return common.stream().map(userStorage::findById).toList();
	}

}
