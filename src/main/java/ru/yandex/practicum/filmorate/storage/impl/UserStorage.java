package ru.yandex.practicum.filmorate.storage.impl;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.InternalServerException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.repositories.impl.UserRepositories;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.util.dtomapper.UserDtoMapper;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.dto.UserDto.Request.Create;
import ru.yandex.practicum.filmorate.dto.UserDto.Request.Update;
import ru.yandex.practicum.filmorate.dto.UserDto.Response.Private;

@Slf4j
@Component
public class UserStorage implements Storage<Private, Create, Update> {

	private final UserRepositories userRepositories;
	private final Map<Long, User> users;

	@Autowired
	public UserStorage(UserRepositories userRepositories) {
		this.userRepositories = userRepositories;
		users = this.userRepositories.query(0).stream().collect(Collectors.toMap(User::getId, Function.identity()));
	}

	@Override
	public Private create(Create userDto) {
		User user = UserDtoMapper.returnUser(userDto);
		log.trace("Создания пользователя: {}", user.toString());
		Long id = userRepositories.add(user)
				.orElseThrow(() -> new InternalServerException("Error create user in database!!!"));
		user.setId(id);
		users.put(user.getId(), user);
		log.trace("Пользователь {} добавлен", user.toString());
		Private ans = UserDtoMapper.returnPrivateDto(user);
		return ans;

	}

	@Override
	public Collection<Private> read() {
		log.trace("Обработка. Чтение всех пользователей");
		return users.values().stream().map(UserDtoMapper::returnPrivateDto).toList();
	}

	@Override
	public Private update(Update userDto) {
		User user = UserDtoMapper.returnUser(userDto);
		log.trace("Обновление пользователя {}", user.toString());
		if (users.containsKey(user.getId())) {
			userRepositories.update(user)
					.orElseThrow(() -> new InternalServerException("Failed user update in database!!!"));
			users.put(user.getId(), user);
			log.trace("Информация о пользователе {} успешно обновлена", user.toString());
			UserDto.Response.Private ans = UserDtoMapper.returnPrivateDto(user);
			return ans;
		}
		log.warn("Пользователь: {}, id пользователя не найден", user.toString());
		throw new NotFoundException(String.format("Пользователь с id: %d не найден!", user.getId()));
	}

	@Override
	public Private findById(final Long id) {
		User user;
		log.trace("Find user by id: {}", id);
		if (users.containsKey(id)) {
			user = users.get(id);
			log.trace("User is find in map: {}", user.toString());
			return UserDtoMapper.returnPrivateDto(user);
		}
		user = userRepositories.query(id).orElseThrow(() -> new NotFoundException("User not found!!!"));
		return UserDtoMapper.returnPrivateDto(user);
	}
}
