package ru.yandex.practicum.filmorate.model.impl;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.WebModel;

@Data
@Builder(toBuilder = true)
public class FilmUser implements WebModel {
	private Long filmId;
	private Long userId;
}
