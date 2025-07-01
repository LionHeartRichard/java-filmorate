package ru.yandex.practicum.filmorate.util.dtomapper;

import ru.yandex.practicum.filmorate.dto.UserDtoCreate;
import ru.yandex.practicum.filmorate.dto.UserDtoUpdate;
import ru.yandex.practicum.filmorate.model.User;

public class DtoMapperUser {

	private DtoMapperUser() {
	}

	public static User getUser(UserDtoCreate dto) {
		String name = dto.hasName() ? dto.getName() : dto.getLogin();
		User user = new User();
		user.setLogin(dto.getLogin());
		user.setName(name);
		user.setEmail(dto.getEmail());
		user.setBirthday(dto.getBirthday());
		return user;
	}

	public static User getUser(User user, UserDtoUpdate dto) {
		if (dto.hasBirthday())
			user.setBirthday(dto.getBirthday());
		if (dto.hasEmail())
			user.setEmail(dto.getEmail());
		if (dto.hasLogin())
			user.setLogin(dto.getLogin());
		if (dto.hasName())
			user.setName(dto.getName());
		return user;
	}

	public static UserDtoCreate getDtoCreate(UserDtoUpdate dto) {
		return new UserDtoCreate(dto.getEmail(), dto.getLogin(), dto.getName(), dto.getBirthday());
	}

}
