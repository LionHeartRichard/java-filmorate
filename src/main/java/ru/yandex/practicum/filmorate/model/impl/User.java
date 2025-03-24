package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;

import lombok.Value;
import ru.yandex.practicum.filmorate.model.WebModel;

@Value
public class User implements WebModel {
	Long id;
	String email;
	String login;
	String name;
	LocalDate birthday;
}
