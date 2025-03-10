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

	@GetMapping("/{id}")
	public User findById(@PathVariable final Long id) {
		log.trace("Поиска пользователя по id: {}", id);
		validId(id);
		return userStorage.findById(id);
	}

	@PutMapping("/{id}/friends/{friendId}")
	public Collection<User> addFriend(@PathVariable(required = false) Long id,
			@PathVariable(required = false) Long friendId) {
		log.trace("Обработка запроса Добавление в друзья id: {}, friendId: {}", id, friendId);
		validIdentifiers(id, friendId);
		return userService.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friendId}")
	public Collection<User> deleteFriend(@PathVariable(required = false) final Long id,
			@PathVariable(required = false) final Long friendId) {
		log.trace("Обработка запроса Удаление из друзей id: {}, friendId: {}", id, friendId);
		validIdentifiers(id, friendId);
		return userService.deleteFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public Collection<User> getFriends(@PathVariable final Long id) {
		log.trace("Обработка запроса на получение друзей пользователя с id: {}", id);
		return userService.getFriends(id);
	}

	@GetMapping("/{id}/friends/common/{otherId}")
	public Collection<User> getCommonFriends(@PathVariable(required = false) final Long id,
			@PathVariable(required = false) final Long otherId) {
		log.trace("Обработка запроса на получение общих друзей");
		validIdentifiers(id, otherId);
		return userService.getCommonFriends(id, otherId);
	}

	private void validId(Long id) {
		if (id < 0) {
			log.warn("Передан ошибочный идентификатор от клиента userId: {}", id);
			throw new NotValidParamException(
					Map.of("Не верный идентиикатор пользователя", "идентификатор - целое положительное число"),
					Map.of("Передан идентификатор пользователя", id + ""));
		}
	}

	private void validIdentifiers(Long id, Long friendId) {
		if (id == null || friendId == null) {
			log.warn("Передан ошибочный идентификатор от клиента userId: {}, friendId: {}", id, friendId);
			throw new NotValidParamException(Map.of("Идентификатор пользователя", id + ""),
					Map.of("Идентификатор друга", friendId + ""),
					Map.of("Идентификаторы", "НЕ МОГУТ иметь значение null!!!"));
		}
		if (id < 0 || friendId < 0) {
			log.warn("Передан ошибочный идентификатор от клиента userId: {}, friendId: {}", id, friendId);
			throw new NotValidParamException(Map.of("Идентификатор пользователя", id + ""),
					Map.of("Идентификатор друга", friendId + ""),
					Map.of("Идентификаторы", "НЕ МОГУТ иметь отрицательное значение!!!"));
		}
	}
}
