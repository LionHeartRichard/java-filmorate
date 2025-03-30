package ru.yandex.practicum.filmorate.controller;

import java.util.Collection;

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
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.UserStorage;
import ru.yandex.practicum.filmorate.util.LocalValidator;
import ru.yandex.practicum.filmorate.dto.FriendDto;
import ru.yandex.practicum.filmorate.dto.UserDto;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final LocalValidator validator;
	private final UserService userService;
	private final UserStorage userStorage;

	@PostMapping
	public UserDto.Response.Private create(@Valid @RequestBody UserDto.Request.Create userDto) {
		log.trace("Обработка запроса на создание пользователя: {}", userDto.toString());
		return userStorage.create(userDto);
	}

	@PutMapping
	public UserDto.Response.Private update(@Valid @RequestBody UserDto.Request.Update userDto) {
		log.trace("Обработка запроса на обновление пользователя: {}", userDto.toString());
		return userStorage.update(userDto);
	}

	@GetMapping
	public Collection<UserDto.Response.Private> read() {
		log.trace("Обработка запроса на получение всех пользователей");
		return userStorage.read();
	}

	@GetMapping("/{id}")
	public UserDto.Response.Private findById(@PathVariable final Long id) {
		log.trace("Поиска пользователя по id: {}", id);
		validator.positiveValue(id,
				String.format("В запросе поиск пользователя по id, передано отрицательное значение: %d", id));
		return userStorage.findById(id);
	}

	@PutMapping("/{id}/friends/{friend_id}")
	public FriendDto.Response.Private addFriend(@PathVariable Long id,
			@PathVariable(value = "friend_id") Long friendId) {
		log.trace("Обработка запроса Добавление в друзья id: {}, friend_id: {}", id, friendId);
		validator.positiveValue(id, String
				.format("В запросе на добавление в друзья, передано отрицательное значение id-пользователя: %d", id));
		validator.positiveValue(friendId, String
				.format("В запросе на добавление в друзья, передано отрицательное значение id-друга: %d", friendId));
		return userService.addFriend(id, friendId);
	}

	@DeleteMapping("/{id}/friends/{friend_id}")
	public void deleteFriend(@PathVariable final Long id, @PathVariable(value = "friend_id") final Long friendId) {
		log.trace("Обработка запроса Удаление из друзей id: {}, friend_id: {}", id, friendId);
		validator.positiveValue(id, String
				.format("В запросе на Удаление из друзей, передано отрицательное значение id-пользователя: %d", id));
		validator.positiveValue(friendId, String
				.format("В запросе на Удаление из друзей, передано отрицательное значение id-друга: %d", friendId));
		userService.deleteFriend(id, friendId);
	}

	@GetMapping("/{id}/friends")
	public Collection<UserDto.Response.Private> getFriends(@PathVariable final Long id) {
		log.trace("Обработка запроса на получение друзей пользователя с id: {}", id);
		validator.positiveValue(id, String.format(
				"В запросе на получение друзей пользователя, передано отрицательное значение id-пользователя: %d", id));
		return userService.getFriends(id);
	}

	@GetMapping("/{id}/friends/common/{other_id}")
	public Collection<UserDto.Response.Private> getCommonFriends(@PathVariable final Long id,
			@PathVariable("other_id") final Long otherId) {
		log.trace("Обработка запроса на получение общих друзей");
		validator.positiveValue(id, String.format(
				"В запросе на получение общих друзей, передано отрицательное значение id-пользователя: %d", id));
		validator.positiveValue(otherId, String.format(
				"В запросе на получение общих друзей, передано отрицательное значение id-другого пользователя: %d",
				otherId));
		return userService.getCommonFriends(id, otherId);
	}
}
