package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;
import ru.yandex.practicum.filmorate.validation.Login;

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
	@Past(message = "Дата рождения не может быть в будущем!!!")
	private LocalDate birthday;
	private Set<Long> friends;

	public User(Long id, @Email(message = "Не корректный адрес электронной почты!!!") String email, String login,
			String name, @Past(message = "Дата рождения не может быть в будущем!!!") LocalDate birthday,
			Set<Long> friends) {
		super();
		this.id = id;
		this.email = email;
		this.login = login;
		this.name = name;
		this.birthday = birthday;
		this.friends = friends == null ? new HashSet<>() : friends;
	}

}
