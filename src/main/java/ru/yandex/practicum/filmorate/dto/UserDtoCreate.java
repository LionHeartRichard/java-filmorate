package ru.yandex.practicum.filmorate.dto;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.validation.Login;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
@Data
public class UserDtoCreate {
	@Email
	String email;
	@Login
	String login;
	@Size(max = 100)
	String name;
	@Past
	LocalDate birthday;

	public boolean hasName() {
		return !(name == null || name.isBlank());
	}
}
