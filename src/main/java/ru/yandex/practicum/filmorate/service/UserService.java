package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {
	private final UserStorage userStorage;

	public Collection<User> addFriend(final Long id, final Long idFriend) {
		User user = userStorage.findById(id);
		User friend = userStorage.findById(idFriend);
		log.trace("Начало обработки в СЕРВИСЕ. Добавление в друзья");
		Set<Long> idxes = user.getFriends();
		Set<Long> idxesFriend = friend.getFriends();
		idxes = idxes == null ? new HashSet<>() : idxes;
		idxesFriend = idxesFriend == null ? new HashSet<>() : idxesFriend;
		if (!idxes.contains(idFriend)) {
			idxes.add(idFriend);
			idxesFriend.add(id);
			user.setFriends(idxes);
			friend.setFriends(idxesFriend);
			userStorage.update(user);
			userStorage.update(friend);
			log.trace("Пользователи: {}, {} теперь друзья", user.toString(), friend.toString());
		}
		return new ArrayList<>(Arrays.asList(user, friend));
	}

	public Collection<User> deleteFriend(final Long id, final Long idFriend) {
		User user = userStorage.findById(id);
		User friend = userStorage.findById(idFriend);
		log.trace("Начало обработки в СЕРВИСЕ. Удаление из друзей");
		@NotNull(message = "Удаление из друзей не возможно, друзя отсутсвуют!")
		Set<Long> idxes = user.getFriends();
		@NotNull(message = "Удаление из друзей не возможно, друзя отсутсвуют!")
		Set<Long> idxesFriend = friend.getFriends();

		if (idxes.contains(idFriend) && idxesFriend.contains(id)) {
			idxes.remove(idFriend);
			idxesFriend.remove(id);
			user.setFriends(idxes);
			friend.setFriends(idxesFriend);
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
		@NotNull(message = "Друзя отсутсвуют! Общих друзей найти не представляет возможности")
		Set<Long> userIdexes = user.getFriends();
		@NotNull(message = "Друзя отсутсвуют! Общих друзей найти не представляет возможности")
		Set<Long> otherIdexes = other.getFriends();
		@NotNull(message = "Общие друзья отсутствуют!!!")
		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
		return common.stream().map(userStorage::findById).toList();
	}
}
