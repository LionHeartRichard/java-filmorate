package ru.yandex.practicum.filmorate.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.yandex.practicum.filmorate.controller.Controller;
import ru.yandex.practicum.filmorate.exception.CustomException;
import ru.yandex.practicum.filmorate.model.impl.User;

@RestController
public class UserController implements Controller<User> {

	private final Map<Long, User> users = new HashMap<>();

	@Override
	@PostMapping
	public User doPost(@Valid @RequestBody User user) {
		if (users.containsKey(user.getId())) {
			if (user.getName() == null || user.getName().isBlank())
				user.setName(user.getLogin());
			users.put(user.getId(), user);
			return user;
		}
		throw new CustomException("Пользователь с id: " + user.getId() + " уже добавлен!");
	}

	@Override
	@GetMapping
	public Collection<User> doGet() {
		return users.values();
	}

	@Override
	@PutMapping
	public User doPut(@Valid @RequestBody User newUser) {
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
			return user;
		}
		throw new CustomException("Пользователь с id: " + newUser.getId() + " не найден!");
	}
}
