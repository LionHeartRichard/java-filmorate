package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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

//	public Collection<User> addFriend(final Long id, final Long friendId) {
//		log.trace("Начало. Добавление в друзья");
//		User user = userStorage.findById(id);
//		User friend = userStorage.findById(friendId);
//		log.trace("Обработка. Добавление в друзья");
////		user.getFriends().add(friendId);
////		friend.getFriends().add(id);
//		return new ArrayList<>(Arrays.asList(user, friend));
//	}
//
//	public void deleteFriend(final Long id, final Long otherId) {
//		log.trace("Начало. Удаление из друзей");
//		User user = userStorage.findById(id);
//		User other = userStorage.findById(otherId);
//		log.trace("Обработка. Удаление из друзей");
////		if (user.getFriends().contains(otherId)) {
////			user.getFriends().remove(otherId);
////			other.getFriends().remove(id);
////			userStorage.update(user);
////			userStorage.update(other);
////			log.trace("Удаление из друзей ПРОИЗВЕДЕНО");
////		}
//	}
//
//	public Collection<User> getFriends(final Long id) {
//		log.trace("Начало. Получение списка друзей");
//		User user = userStorage.findById(id);
//		log.trace("Обработка. Получение списка друзей");
//		return new ArrayList<>();
////		return user.getFriends().stream().map(userStorage::findById).toList();
//	}
//
//	public Collection<User> getCommonFriends(final Long id, final Long otherId) {
//		log.trace("Начало. Получение списка общих друзей");
//		User user = userStorage.findById(id);
//		User other = userStorage.findById(otherId);
//
//		return new ArrayList<>();
//
////		log.trace("Обработка. Получение списка общих друзей");
////		Set<Long> userIdexes = user.getFriends();
////		Set<Long> otherIdexes = other.getFriends();
////
////		Set<Long> common = userIdexes.stream().filter(otherIdexes::contains).collect(Collectors.toSet());
////		common = common == null ? new HashSet<>() : common;
////		return common.stream().map(userStorage::findById).toList();
//	}

}
