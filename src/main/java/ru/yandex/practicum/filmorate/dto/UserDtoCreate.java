package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.Login;

@Data
public class UserDtoCreate {
	@Email
	private final String email;
	@Login
	private final String login;
	@Size(max = 100)
	private final String name;
	@Past
	private final LocalDate birthday;

	public boolean hasName() {
		return !(name == null || name.isBlank());
	}
}
