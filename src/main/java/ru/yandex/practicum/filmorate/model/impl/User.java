package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.Login;
import ru.yandex.practicum.filmorate.validation.BeforeNow;

@Validated
@Data
@Builder(toBuilder = true)
public class User implements WebModel {

	private Long id;
	@Email
	private String email;
	@Login
	private String login;
	private String name;
	@BeforeNow
	private LocalDate birthday;
}
