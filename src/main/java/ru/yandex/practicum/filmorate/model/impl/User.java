package ru.yandex.practicum.filmorate.model.impl;

import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class User implements WebModel {
	private Long id;
	private String email;
	private String login;
	private String name;
	private LocalDate birthday;
	private Set<Long> firends;
}
