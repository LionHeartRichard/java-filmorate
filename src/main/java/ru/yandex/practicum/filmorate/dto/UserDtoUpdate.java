package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.time.Period;

import lombok.Data;

@Data
public class UserDtoUpdate {

	private final Long id;
	private final String email;
	private final String login;
	private final String name;
	private final LocalDate birthday;

	public boolean hasEmail() {
		return !(email == null || email.isBlank());
	}

	public boolean hasLogin() {
		return !(login == null || login.isBlank());
	}

	public boolean hasName() {
		return !(name == null || name.isBlank());
	}

	public boolean hasBirthday() {
		if (birthday == null)
			return false;

		int age = Period.between(birthday, LocalDate.now()).getYears();
		return age >= 5 && age < 150;
	}
}
