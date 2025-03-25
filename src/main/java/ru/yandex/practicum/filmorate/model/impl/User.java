package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class User implements WebModel {
	Long id;
	String email;
	String login;
	String name;
	LocalDate birthday;
}
