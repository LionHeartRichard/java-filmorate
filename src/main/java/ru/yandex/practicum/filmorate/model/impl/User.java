package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.Birthday;
import ru.yandex.practicum.filmorate.validation.ValidLogin;

@Data
@Builder(toBuilder = true)
public class User implements WebModel {
	@NonNull
	private Long id;
	@Email
	private String email;
	@ValidLogin
	private String login;
	private String name;
	@Birthday
	private LocalDate birthday;
}
