package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotValidParamException;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;
import ru.yandex.practicum.filmorate.validation.NotNegativeValue;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserStorage userStorage;

	@PostMapping
	public User create(@Valid @RequestBody User user) {
		log.trace("Обработка запроса на создание пользователя: {}", user.toString());
		return userStorage.create(user);
	}

	@PutMapping
	public User update(@Valid @RequestBody User newUser) {
		log.trace("Обработка запроса на обновление пользователя: {}", newUser.toString());
		return userStorage.update(newUser);
	}

	@GetMapping
	public Collection<User> read() {
		log.trace("Обработка запроса на получение всех пользователей");
		return userStorage.read();
	}

	@GetMapping("/{id}")
	public User findById(@NotNegativeValue @PathVariable final Long id) {
		log.trace("Поиска пользователя по id: {}", id);
		return userStorage.findById(id);
	}

	@PutMapping("/{id}/friends/{friend_id}")
	public Collection<User> addFriend(@NotNegativeValue @PathVariable Long id,
			@NotNegativeValue @PathVariable(value = "friend_id") Long idFriend) {
		log.trace("Обработка запроса Добавление в друзья id: {}, idFriend: {}", id, idFriend);
		return userService.addFriend(id, idFriend);
	}

	@DeleteMapping("/{id}/friends/{friend_id}")
	public Collection<User> deleteFriend(@NotNegativeValue @PathVariable final Long id,
			@NotNegativeValue @PathVariable(value = "friend_id") final Long idFriend) {
		log.trace("Обработка запроса Удаление из друзей id: {}, friendId: {}", id, idFriend);
		return userService.deleteFriend(id, idFriend);
	}

	@GetMapping("/{id}/friends")
	public Collection<User> getFriends(@NotNegativeValue @PathVariable final Long id) {
		log.trace("Обработка запроса на получение друзей пользователя с id: {}", id);
		return userService.getFriends(id);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public Collection<User> getCommonFriends(@NotNegativeValue @PathVariable final Long id,
			@NotNegativeValue @PathVariable final Long otherId) {
		log.trace("Обработка запроса на получение общих друзей");
		return userService.getCommonFriends(id, otherId);
	}
}
