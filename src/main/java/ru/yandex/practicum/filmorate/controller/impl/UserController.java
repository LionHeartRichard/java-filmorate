package ru.yandex.practicum.filmorate.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.Controller;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.User;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController implements Controller<User> {

	private final Map<Long, User> users = new HashMap<>();

	@Override
	@PostMapping
	public User doPost(@Valid @RequestBody User user) {
		log.trace("Валдидация пройдена, начало обработки POST запроса для создания/добавления пользователя {}",
				user.toString());
		if (user.getId() == null) {
			user.setId(nextId());
			if (user.getName() == null || user.getName().isBlank())
				user.setName(user.getLogin());
			users.put(user.getId(), user);
			log.trace("Пользователь с id: {} добавлен", user.getId());
			return user;
		}
		log.warn("Пользователь не добавлен, id создано в ручную {}", user.getId());
		throw new CustomException(String.format(
				"Пользователь с id: %d добавить не возможно! Не указываейте идентификатор, он генерируется автоматически!",
				user.getId()));
	}

	@Override
	@GetMapping
	public Collection<User> doGet() {
		return users.values();
	}

	@Override
	@PutMapping
	public User doPut(@Valid @RequestBody User newUser) {
		log.trace("Валидация пройдена, начало обработки PUT запроса для объекта {}", newUser.toString());
		if (users.containsKey(newUser.getId())) {
			User user = users.get(newUser.getId());
			if (newUser.getBirthday() != null)
				user.setBirthday(newUser.getBirthday());
			if (newUser.getEmail() != null)
				user.setEmail(newUser.getEmail());
			if (newUser.getLogin() != null)
				user.setLogin(newUser.getLogin());
			if (newUser.getName() != null)
				user.setName(newUser.getName());
			users.put(user.getId(), user);
			log.trace("Информация о пользователе {} успешно обновлена", user.toString());
			return user;
		}
		log.warn("Попытка обновить информацию о пользователе {}, id пользователя не найден!", newUser.toString());
		throw new CustomException(String.format("Пользователь с id: %d не найден!", newUser.getId()));
	}

	private Long nextId() {
		Long id = users.keySet().stream().mapToLong(key -> key).max().orElse(0);
		return ++id;
	}
}
