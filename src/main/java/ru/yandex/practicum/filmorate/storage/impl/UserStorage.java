package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.impl.UserRepositories;
import ru.yandex.practicum.filmorate.storage.Storage;

@Slf4j
@Component
public class UserStorage implements Storage<User> {

	private final UserRepositories userRepositories;
	private final Map<Long, User> users;

	@Autowired
	public UserStorage(UserRepositories userRepositories) {
		this.userRepositories = userRepositories;
		users = userRepositories.query().stream().collect(Collectors.toMap(User::getId, Function.identity()));
	}

	@Override
	public User create(User user) {
		log.trace("Создания пользователя: {}", user.toString());
		if (user.getId() == null) {
			if (user.getName() == null || user.getName().isBlank())
				user.setName(user.getLogin());
			Long id = userRepositories.add(user)
					.orElseThrow(() -> new InternalServerException("Error create user in database!!!"));
			user.setId(id);
			users.put(user.getId(), user);
			log.trace("Пользователь {} добавлен", user.toString());
			return user;
		}
		log.warn("Пользователь {} не добавлен, id создано в ручную", user.toString());
		throw new ConditionsNotMetException(String.format(
				"Пользователь с id: %d добавить не возможно! Не указываейте идентификатор, он генерируется автоматически!",
				user.getId()));
	}

	@Override
	public Collection<User> read() {
		log.trace("Обработка. Чтение всех пользователей");
		return users.values();
	}

	@Override
	public User update(final User user) {
		log.trace("Обновление пользователя {}", user.toString());
		if (users.containsKey(user.getId())) {
			userRepositories.update(user)
					.orElseThrow(() -> new InternalServerException("Failed user update in database!!!"));
			users.put(user.getId(), user);
			log.trace("Информация о пользователе {} успешно обновлена", user.toString());
			return user;
		}
		log.warn("Пользователь: {}, id пользователя не найден", user.toString());
		throw new NotFoundException(String.format("Пользователь с id: %d не найден!", user.getId()));
	}

	@Override
	public User findById(final Long id) {
		log.trace("Find user by id: {}", id);
		if (users.containsKey(id)) {
			User user = users.get(id);
			log.trace("User is find in map: {}", user.toString());
			return user;
		}
		return userRepositories.query(id).orElseThrow(() -> new NotFoundException("User not found!!!"));
	}
}
