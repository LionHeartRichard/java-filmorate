package ru.yandex.practicum.filmorate.util.dtomapper;

import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.dto.UserDto.Request.Create;
import ru.yandex.practicum.filmorate.dto.UserDto.Request.Update;
import ru.yandex.practicum.filmorate.dto.UserDto.Response.Public;
import ru.yandex.practicum.filmorate.dto.UserDto.Response.Private;

public class UserDtoMapper {

	private UserDtoMapper() {
	}

	public static User returnUser(Create dto) {
		String name = dto.getName() == null ? dto.getLogin() : dto.getName();
		User user = User.builder().id(null).email(dto.getEmail()).login(dto.getLogin()).name(name)
				.birthday(dto.getBirthday()).build();
		return user;
	}

	public static User returnUser(Update dto) {
		User user = User.builder().id(dto.getId()).email(dto.getEmail()).login(dto.getLogin())
				.name(dto.getName()).birthday(dto.getBirthday()).build();
		return user;
	}

	public static Public returnPublicDto(User user) {
		Public dto = new Public(user.getLogin(), user.getName());
		return dto;
	}

	public static Private returnPrivateDto(User user) {
		Private dto = new Private(user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
		return dto;
	}
}
