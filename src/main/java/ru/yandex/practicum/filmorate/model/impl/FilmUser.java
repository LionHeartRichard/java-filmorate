package ru.yandex.practicum.filmorate.model.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@AllArgsConstructor
public class FilmUser implements WebModel {
	private Long filmId;
	private Long userId;
}
