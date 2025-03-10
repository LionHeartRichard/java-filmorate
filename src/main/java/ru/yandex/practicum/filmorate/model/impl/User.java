package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;
import java.util.Set;

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
	@Email(message = "Не корректный адрес электронной почты!!!")
	private String email;
	@Login(message = "По првилам логин не может быть пустым или содержать пробелы!!!")
	private String login;
	private String name;
	@BeforeNow(message = "Дата рождения не может быть в будущем!!!")
	private LocalDate birthday;
	private Set<Long> friends;
}
