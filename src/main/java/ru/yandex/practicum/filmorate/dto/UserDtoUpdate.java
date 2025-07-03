package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;
import java.time.Period;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Data
public class UserDtoUpdate {

	Long id;
	String email;
	String login;
	String name;
	LocalDate birthday;

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
